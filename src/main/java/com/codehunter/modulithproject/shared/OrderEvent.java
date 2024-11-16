package com.codehunter.modulithproject.shared;

public record OrderEvent(OrderDTO order, OrderEventType orderEventType) {

    public enum OrderEventType {
        CREATED,
        PAYMENT_REQUESTED,
        CANCELLED
    }

}
