package com.amu.ecommerce.kafka;

import com.amu.ecommerce.dto.CustomerResponse;
import com.amu.ecommerce.dto.PurchaseResponse;
import com.amu.ecommerce.entities.PaymentMethod;

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
