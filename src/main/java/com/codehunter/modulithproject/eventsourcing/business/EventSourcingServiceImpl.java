package com.codehunter.modulithproject.eventsourcing.business;

import com.codehunter.modulithproject.eventsourcing.EventSourcingService;
import com.codehunter.modulithproject.eventsourcing.OrderEvent;
import com.codehunter.modulithproject.eventsourcing.PaymentEvent;
import com.codehunter.modulithproject.eventsourcing.WarehouseEvent;
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
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void addPaymentEvent(PaymentEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void addWarehouseEvent(WarehouseEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
