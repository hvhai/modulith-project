package com.codehunter.modulithproject.order.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "fruit_order_payment")
@Slf4j
@Getter
public class JpaOrderPayment {

    @Id
    @Setter
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Setter
    JpaOrder order;

    @Setter
    BigDecimal totalAmount;

    @Setter
    Instant purchaseAt;
}
