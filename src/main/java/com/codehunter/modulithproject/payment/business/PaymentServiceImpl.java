package com.codehunter.modulithproject.payment.business;


import com.codehunter.modulithproject.payment.PaymentService;
import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import com.codehunter.modulithproject.payment.jpa_repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void createPayment(CreatePaymentRequest request) {
        JpaPayment newPayment = new JpaPayment();
        newPayment.setOrderId(request.orderId());
        newPayment.setTotalAmount(request.totalAmount());
        JpaPayment createdPayment = paymentRepository.save(newPayment);
        log.info("Payment created id={} , orderId={}", createdPayment.getId(), createdPayment.getOrderId());
    }
}
