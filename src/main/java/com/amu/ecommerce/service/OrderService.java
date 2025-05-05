package com.amu.ecommerce.service;

import com.amu.ecommerce.client.CustomerClient;
import com.amu.ecommerce.client.ProductClient;
import com.amu.ecommerce.dto.OrderLineRequest;
import com.amu.ecommerce.dto.OrderRequest;
import com.amu.ecommerce.dto.PurchaseRequest;
import com.amu.ecommerce.exception.BusinessException;
import com.amu.ecommerce.mapper.OrderMapper;
import com.amu.ecommerce.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createdOrder(@Valid OrderRequest request) {
        //check customer
        var customer = this.customerClient.findCustomerById((request.customerId()))
                .orElseThrow(() -> new BusinessException("Cannot create order::No customer exists with the provided id::" + request.customerId()));
        //purchase the products -> product-ms (RestTemplate)
        this.productClient.purchaseResponses(request.products());
        //persist order
        var order = this.repository.save(mapper.toOrder(request));

        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.getProductId(),
                            purchaseRequest.getQuantity()
                    )
            );
        }

        //start payment process

        //send the order confirmation --> notification-ms(kafka)

        return null;
    }
}
