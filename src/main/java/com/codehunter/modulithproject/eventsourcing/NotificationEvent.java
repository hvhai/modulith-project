package com.codehunter.modulithproject.eventsourcing;

//@Externalized("notification.fruit-ordering")
public record NotificationEvent(String orderId, NotificationEventType notificationEventType, String message) {
    public enum NotificationEventType {
        ORDER_CREATED,
        PAYMENT_COMPLETED,
        PAYMENT_FAILED,
        ORDER_COMPLETED,
        ORDER_CANCELLED,
    }
}
