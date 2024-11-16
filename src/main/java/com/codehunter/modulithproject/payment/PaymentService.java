package com.codehunter.modulithproject.payment;

import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.shared.PaymentDTO;

import java.util.List;

public interface PaymentService {
    PaymentDTO purchasePayment(String id);

    PaymentDTO getPayment(String id);

    List<PaymentDTO> getAllPayments();

    void createPayment(OrderDTO request);
}
