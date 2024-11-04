package com.codehunter.modulithproject.payment.jpa;

import com.codehunter.modulithproject.payment.mapper.PaymentMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "fruit_payment_payment")
@Slf4j
@Getter
public class JpaPayment extends AbstractAggregateRoot<JpaPayment> {
    private static final PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Setter
    String orderId;

    @Setter
    BigDecimal totalAmount;

    @Setter
    Instant purchaseAt;

    public JpaPayment() {
    }

    @PostPersist
    void postPersist() {
        log.info("[PostPersist] Payment persisted with id {}", this.id);
    }

    public JpaPayment(String orderId, BigDecimal totalAmount) {
        log.info("Create payment for: orderId={}, totalAmount={}", orderId, totalAmount);
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    public JpaPayment purchase() {
        log.info("Purchase payment id={}, orderId={}", this.id, this.orderId);
        this.purchaseAt = Instant.now();
        return this;
    }
}
