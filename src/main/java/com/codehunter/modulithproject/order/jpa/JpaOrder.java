package com.codehunter.modulithproject.order.jpa;

import com.codehunter.modulithproject.order.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Table(name = "fruit_order_order")
@Slf4j
public class JpaOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;


    @OneToMany(mappedBy = "order")
    List<JpaOrderProduct> productList;

    @PostPersist
    void postPersist() {
        log.info("Order create with id {}", this.id);
    }
}
