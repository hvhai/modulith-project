package com.codehunter.modulithproject.payment.business;

import com.codehunter.modulithproject.eventsourcing.OrderEvent;
import com.codehunter.modulithproject.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventHandler {
    private final PaymentService paymentService;

    public PaymentEventHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @ApplicationModuleListener
    public void onOrderEvent(OrderEvent orderEvent) {
        log.info("[Payment]Consume Order event {}", orderEvent.orderEventType());
        switch (orderEvent.orderEventType()) {
            case CREATED, CANCELLED:
                log.info("Do nothing");
                break;
            case IN_PAYMENT:
                paymentService.createPayment(
                        new PaymentService.CreatePaymentRequest(
                                orderEvent.order().id(), orderEvent.order().totalAmount()));
                break;
        }

    }
}
