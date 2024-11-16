package com.codehunter.modulithproject.order.jpa;

import com.codehunter.modulithproject.shared.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fruit_order_order")
@Slf4j
@Getter
public class JpaOrder extends AbstractAggregateRoot<JpaOrder> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Setter
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Setter
    BigDecimal totalAmount;

    @OneToOne(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Setter
    JpaOrderPayment payment;


    @Setter
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "fruit_order_order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    Set<JpaOrderProduct> products = new HashSet<>();

    public JpaOrder() {

    }

    @PostPersist
    void postPersist() {
        log.info("[PostPersist] Order persisted with id {}", this.id);
    }

    public JpaOrder(Set<JpaOrderProduct> products) {
        log.info("Create Order");
        this.products = products;
        this.orderStatus = OrderStatus.IN_PRODUCT_PREPARE;
    }

    public JpaOrder registerForPayment() {
        log.info("Register payment for Order id={}", this.id);
        this.orderStatus = OrderStatus.IN_PAYMENT_REQUESTED;
        BigDecimal totalAmount = this.products.stream()
                .map(JpaOrderProduct::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalAmount = totalAmount;
        return this;
    }

    public JpaOrder waitingForPayment(JpaOrderPayment payment) {
        log.info("Register payment success, update order id={}", this.id);
        this.payment = payment;
        this.orderStatus = OrderStatus.WAITING_FOR_PURCHASE;
        return this;
    }

    public JpaOrder finish() {
        log.info("Purchase success, update order id={}", this.id);
        this.orderStatus = OrderStatus.DONE;
        return this;
    }

    public JpaOrder cancel() {
        log.info("Cancel order id={}", this.id);
        this.orderStatus = OrderStatus.CANCELED;
        return this;
    }
}
