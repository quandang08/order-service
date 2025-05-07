package com.amu.ecommerce.service;

import com.amu.ecommerce.client.CustomerClient;
import com.amu.ecommerce.client.ProductClient;
import com.amu.ecommerce.dto.OrderLineRequest;
import com.amu.ecommerce.dto.OrderRequest;
import com.amu.ecommerce.dto.OrderResponse;
import com.amu.ecommerce.dto.PurchaseRequest;
import com.amu.ecommerce.exception.BusinessException;
import com.amu.ecommerce.kafka.OrderConfirmation;
import com.amu.ecommerce.kafka.OrderProducer;
import com.amu.ecommerce.mapper.OrderMapper;
import com.amu.ecommerce.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createdOrder(@Valid OrderRequest request) {
        //check customer
        var customer = this.customerClient.findCustomerById((request.customerId()))
                .orElseThrow(() -> new BusinessException("Cannot create order::No customer exists with the provided id::" + request.customerId()));
        //purchase the products -> product-ms (RestTemplate)
        var purchasedProducts = this.productClient.purchaseResponses(request.products());
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
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.totalAmount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }
}
