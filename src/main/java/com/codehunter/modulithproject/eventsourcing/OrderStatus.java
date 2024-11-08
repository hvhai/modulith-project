package com.codehunter.modulithproject.eventsourcing;

public enum OrderStatus {
    PENDING,
    IN_PRODUCT_PREPARE,
    IN_PAYMENT_REQUESTED,
    WAITING_FOR_PURCHASE,
    DONE,
    CANCELING,
    CANCELED
}
