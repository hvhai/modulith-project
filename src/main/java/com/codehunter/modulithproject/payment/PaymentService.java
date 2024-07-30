package com.codehunter.modulithproject.payment;

import java.math.BigDecimal;

public interface PaymentService {
    record CreatePaymentRequest(String orderId, BigDecimal totalAmount) {
    }

    void createPayment(CreatePaymentRequest request);
}
