package com.codehunter.modulithproject.order.jpa;

import com.codehunter.modulithproject.order.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fruit_order_order")
@Slf4j
@Getter
public class JpaOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Setter
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;


    @Setter
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "fruit_order_order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<JpaOrderProduct> productList = new ArrayList<>();

    @PostPersist
    void postPersist() {
        log.info("Order create with id {}", this.id);
    }
}
