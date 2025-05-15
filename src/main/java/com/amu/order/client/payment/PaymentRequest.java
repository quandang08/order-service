package com.amu.order.client.payment;

import com.amu.order.dto.CustomerResponse;
import com.amu.order.entities.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
