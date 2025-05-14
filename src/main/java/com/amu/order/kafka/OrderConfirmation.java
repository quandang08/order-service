package com.amu.order.kafka;

import com.amu.order.dto.CustomerResponse;
import com.amu.order.dto.PurchaseResponse;
import com.amu.order.entities.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
