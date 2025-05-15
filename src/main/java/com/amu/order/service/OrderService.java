package com.amu.order.service;

import com.amu.order.client.customer.CustomerClient;
import com.amu.order.client.payment.PaymentClient;
import com.amu.order.client.payment.PaymentRequest;
import com.amu.order.client.product.ProductClient;
import com.amu.order.dto.OrderLineRequest;
import com.amu.order.dto.OrderRequest;
import com.amu.order.dto.OrderResponse;
import com.amu.order.dto.PurchaseRequest;
import com.amu.order.exception.BusinessException;
import com.amu.order.kafka.OrderConfirmation;
import com.amu.order.kafka.OrderProducer;
import com.amu.order.mapper.OrderMapper;
import com.amu.order.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final PaymentClient paymentClient;

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

        var paymentRequest = new PaymentRequest(
                request.totalAmount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

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

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find order::" + orderId));
    }
}
