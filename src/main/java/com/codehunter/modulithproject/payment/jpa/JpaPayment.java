package com.codehunter.modulithproject.payment.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "fruit_payment_payment")
@Slf4j
@Getter
public class JpaPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Setter
    String orderId;

    @Setter
    BigDecimal totalAmount;

    @Setter
    Instant purchaseAt;
}
