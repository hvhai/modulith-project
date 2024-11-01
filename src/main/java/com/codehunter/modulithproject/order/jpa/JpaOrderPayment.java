package com.codehunter.modulithproject.order.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "fruit_order_payment")
@Slf4j
@Getter
public class JpaOrderPayment extends AbstractAggregateRoot<JpaOrderPayment> {

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

    @PostPersist
    void postPersist() {
        log.info("[PostPersist] JpaOrderPayment persisted with id {}", this.id);
    }

    public JpaOrderPayment() {
    }

    public JpaOrderPayment(String id, JpaOrder order, BigDecimal totalAmount) {
        this.id = id;
        this.order = order;
        this.totalAmount = totalAmount;
    }
}
