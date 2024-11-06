package com.codehunter.modulithproject.eventsourcing;

public record PaymentEvent(PaymentDTO payment, PaymentEventType paymentEventType) {
    public enum PaymentEventType {
        CREATED,
        PURCHASED,
    }
}
