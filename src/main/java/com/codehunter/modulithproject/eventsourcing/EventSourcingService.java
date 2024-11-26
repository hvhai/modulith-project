package com.codehunter.modulithproject.eventsourcing;

import com.codehunter.modulithproject.shared.NotificationEvent;
import com.codehunter.modulithproject.shared.OrderEvent;
import com.codehunter.modulithproject.shared.PaymentEvent;
import com.codehunter.modulithproject.shared.WarehouseEvent;

public interface EventSourcingService {
    void addOrderEvent(OrderEvent event);

    void addPaymentEvent(PaymentEvent event);

    void addWarehouseEvent(WarehouseEvent event);

    void addNotificationEvent(NotificationEvent event);
}
