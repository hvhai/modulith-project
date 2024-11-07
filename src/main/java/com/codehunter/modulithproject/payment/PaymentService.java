package com.codehunter.modulithproject.payment;

import com.codehunter.modulithproject.eventsourcing.OrderDTO;
import com.codehunter.modulithproject.eventsourcing.PaymentDTO;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    PaymentDTO purchasePayment(String id);

    PaymentDTO getPayment(String id);

    List<PaymentDTO> getAllPayments();

    record CreatePaymentRequest(String orderId, BigDecimal totalAmount) {
    }

    void createPayment(OrderDTO request);
}
