package com.codehunter.modulithproject.payment.business;

import com.codehunter.modulithproject.payment.PaymentCreatedEvent;
import com.codehunter.modulithproject.payment.PaymentDTO;
import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import com.codehunter.modulithproject.payment.mapper.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentInternalEventHandler {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PaymentMapper paymentMapper;

    public PaymentInternalEventHandler(ApplicationEventPublisher applicationEventPublisher, PaymentMapper paymentMapper) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.paymentMapper = paymentMapper;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePaymentCreatedEvent(JpaPayment.PaymentCreateEvent event) {
        log.info("Handle payment created event: {}", event.payment().getId());
        PaymentDTO paymentDTO = paymentMapper.toPaymentDTO(event.payment());
        applicationEventPublisher.publishEvent(new PaymentCreatedEvent(paymentDTO));
    }
}
