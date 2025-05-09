package com.amu.ecommerce.mapper;

import com.amu.ecommerce.dto.OrderLineRequest;
import com.amu.ecommerce.dto.OrderLineResponse;
import com.amu.ecommerce.entities.Order;
import com.amu.ecommerce.entities.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLine  toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .productId(request.productId())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getId(), orderLine.getQuantity());
    }
}
