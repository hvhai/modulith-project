package com.codehunter.modulithproject.payment.business;


import com.codehunter.modulithproject.payment.PaymentCreatedEvent;
import com.codehunter.modulithproject.payment.PaymentDTO;
import com.codehunter.modulithproject.payment.PaymentPurchasedEvent;
import com.codehunter.modulithproject.payment.PaymentService;
import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import com.codehunter.modulithproject.payment.jpa_repository.PaymentRepository;
import com.codehunter.modulithproject.payment.mapper.PaymentMapper;
import com.codehunter.modulithproject.shared.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
        payment.setPurchaseAt(Instant.now());
        JpaPayment updatedPayment = paymentRepository.save(payment);
        log.info("Payment purchase id={}, orderId={}", updatedPayment.getId(), updatedPayment.getOrderId());
        PaymentDTO paymentDTO = paymentMapper.toPaymentDTO(updatedPayment);
        applicationEventPublisher.publishEvent(new PaymentPurchasedEvent(paymentDTO));
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
    public void createPayment(CreatePaymentRequest request) {
        JpaPayment newPayment = new JpaPayment();
        newPayment.setOrderId(request.orderId());
        newPayment.setTotalAmount(request.totalAmount());
        JpaPayment createdPayment = paymentRepository.save(newPayment);
        log.info("Payment created id={} , orderId={}", createdPayment.getId(), createdPayment.getOrderId());

        PaymentDTO paymentDTO = paymentMapper.toPaymentDTO(createdPayment);
        applicationEventPublisher.publishEvent(new PaymentCreatedEvent(paymentDTO));
    }
}
