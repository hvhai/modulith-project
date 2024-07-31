package com.codehunter.modulithproject.payment;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentDTO purchasePayment(String id);

    record CreatePaymentRequest(String orderId, BigDecimal totalAmount) {
    }

    void createPayment(CreatePaymentRequest request);
}
