package com.codehunter.modulithproject.eventsourcing;

import com.codehunter.modulithproject.shared.PaymentDTO;

public record PaymentEvent(PaymentDTO payment, PaymentEventType paymentEventType) {
    public enum PaymentEventType {
        CREATED,
        PURCHASED,
    }
}
