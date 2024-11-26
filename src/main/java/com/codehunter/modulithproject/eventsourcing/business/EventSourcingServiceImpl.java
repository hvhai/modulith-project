package com.codehunter.modulithproject.eventsourcing.business;

import com.codehunter.modulithproject.eventsourcing.EventSourcingService;
import com.codehunter.modulithproject.eventsourcing.NotificationEvent;
import com.codehunter.modulithproject.eventsourcing.OrderEvent;
import com.codehunter.modulithproject.eventsourcing.PaymentEvent;
import com.codehunter.modulithproject.eventsourcing.WarehouseEvent;
import io.opentelemetry.api.trace.Span;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EventSourcingServiceImpl implements EventSourcingService {
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventSourcingServiceImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void addOrderEvent(OrderEvent event) {
        Span span = Span.current();
        span.setAttribute("event.publish", event.orderEventType().toString());
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void addPaymentEvent(PaymentEvent event) {
        Span span = Span.current();
        span.setAttribute("event.publish", event.paymentEventType().toString());
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void addWarehouseEvent(WarehouseEvent event) {
        Span span = Span.current();
        span.setAttribute("event.publish", event.warehouseEventType().toString());
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void addNotificationEvent(NotificationEvent event) {
        Span span = Span.current();
        span.setAttribute("event.publish", event.notificationEventType().toString());
        applicationEventPublisher.publishEvent(event);
    }
}
