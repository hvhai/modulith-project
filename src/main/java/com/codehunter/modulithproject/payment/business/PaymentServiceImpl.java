package com.codehunter.modulithproject.payment.business;


import com.codehunter.modulithproject.eventsourcing.EventSourcingService;
import com.codehunter.modulithproject.payment.PaymentService;
import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import com.codehunter.modulithproject.payment.jpa_repository.PaymentRepository;
import com.codehunter.modulithproject.payment.mapper.PaymentMapper;
import com.codehunter.modulithproject.shared.IdNotFoundException;
import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.shared.PaymentDTO;
import com.codehunter.modulithproject.shared.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final EventSourcingService eventSourcingService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, EventSourcingService eventSourcingService) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.eventSourcingService = eventSourcingService;
    }

    @Override
    @Transactional
    public PaymentDTO purchasePayment(String id) {
        Optional<JpaPayment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isEmpty()) {
            throw new IdNotFoundException(String.format("Payment not found, id=%s", id));
        }
        JpaPayment payment = paymentOptional.get();
        JpaPayment updatedPayment = paymentRepository.save(payment.purchase());
        PaymentDTO paymentDTO = paymentMapper.toPaymentDTO(updatedPayment);
        log.info("[PaymentPurchasedEvent]Payment is purchased for OrderId={}", payment.getOrderId());
//        applicationEventPublisher.publishEvent(new PaymentPurchasedEvent(paymentDTO));
        eventSourcingService.addPaymentEvent(new PaymentEvent(paymentDTO, PaymentEvent.PaymentEventType.PURCHASED));
        return paymentDTO;
    }

    @Override
    public PaymentDTO getPayment(String id) {
        Optional<JpaPayment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isEmpty()) {
            throw new IdNotFoundException(String.format("Payment not found, id=%s", id));
        }
        return paymentMapper.toPaymentDTO(paymentOptional.get());
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<JpaPayment> allOrders = paymentRepository.findAll();
        return paymentMapper.toPaymentDTO(allOrders);
    }

    @Override
    @Transactional
    public void createPayment(OrderDTO request) {
        JpaPayment newPayment = new JpaPayment(request.id(), request.totalAmount());
        JpaPayment createdPayment = paymentRepository.save(newPayment);
        PaymentDTO paymentDTO = paymentMapper.toPaymentDTO(createdPayment);
        log.info("[PaymentCreatedEvent]Payment created for OrderId={}", createdPayment.getOrderId());
//        applicationEventPublisher.publishEvent(new PaymentCreatedEvent(paymentDTO));
        eventSourcingService.addPaymentEvent(new PaymentEvent(paymentDTO, PaymentEvent.PaymentEventType.CREATED));
    }
}
