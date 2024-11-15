package com.codehunter.modulithproject.payment.business;


import com.codehunter.modulithproject.payment.PaymentEvent;
import com.codehunter.modulithproject.payment.PaymentService;
import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import com.codehunter.modulithproject.payment.jpa_repository.PaymentRepository;
import com.codehunter.modulithproject.payment.mapper.PaymentMapper;
import com.codehunter.modulithproject.shared.IdNotFoundException;
import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.shared.PaymentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.applicationEventPublisher = applicationEventPublisher;
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
        applicationEventPublisher.publishEvent(new PaymentEvent(paymentDTO, PaymentEvent.PaymentEventType.PURCHASED));
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
        applicationEventPublisher.publishEvent(new PaymentEvent(paymentDTO, PaymentEvent.PaymentEventType.CREATED));
    }
}
