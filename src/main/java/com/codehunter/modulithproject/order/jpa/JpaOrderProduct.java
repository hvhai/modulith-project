package com.codehunter.modulithproject.order.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fruit_order_product")
@Getter
@Setter
@Slf4j
public class JpaOrderProduct {
    @Id
    String id;

    String name;

    BigDecimal price;

    @ManyToMany(mappedBy = "products")
    Set<JpaOrder> orders = new HashSet<>();

    @Override
    public String toString() {
        return "JpaOrderProduct{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", price=" + price +
               '}';
    }

    @PostPersist
    void postPersist() {
        log.info("Product create {}", this);
    }
}
