package com.codehunter.modulithproject.eventsourcing;

public record OrderEvent(OrderDTO order, OrderEventType orderEventType) {

    public enum OrderEventType {
        CREATED,
        IN_PAYMENT,
        CANCELLED
    }

}
