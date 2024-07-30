package com.codehunter.modulithproject.order.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Table(name = "fruit_order_product")
@Getter
@Setter
@Slf4j
public class JpaOrderProduct {
    @Id
    String id;

    String name;

    @ManyToMany(mappedBy = "productList")
    List<JpaOrder> orderList;

    @Override
    public String toString() {
        return "JpaProduct{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }

    @PostPersist
    void postPersist() {
        log.info("Product create {}", this);
    }
}
