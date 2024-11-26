package com.codehunter.modulithproject.eventsourcing;

import com.codehunter.modulithproject.shared.OrderDTO;

public record OrderEvent(OrderDTO order, OrderEventType orderEventType) {

    public enum OrderEventType {
        CREATED,
        PAYMENT_REQUESTED,
        CANCELLED
    }

}
