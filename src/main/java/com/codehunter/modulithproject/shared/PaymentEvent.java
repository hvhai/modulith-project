package com.codehunter.modulithproject.shared;

public record PaymentEvent(PaymentDTO payment, PaymentEventType paymentEventType) {
    public enum PaymentEventType {
        CREATED,
        PURCHASED,
    }
}
