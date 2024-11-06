package com.codehunter.modulithproject.eventsourcing;

public interface EventSourcingService {
    void addOrderEvent(OrderEvent event);
    void addPaymentEvent(PaymentEvent event);
    void addWarehouseEvent(WarehouseEvent event);
}
