package com.codehunter.modulithproject.order.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fruit_order_product")
@Getter
public class JpaProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Setter
    String name;

    @ManyToOne
    JpaOrder order;

    @Override
    public String toString() {
        return "JpaProduct{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
