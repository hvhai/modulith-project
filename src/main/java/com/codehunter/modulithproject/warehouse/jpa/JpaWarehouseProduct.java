package com.codehunter.modulithproject.warehouse.jpa;

import com.codehunter.modulithproject.order.jpa.JpaOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fruit_warehouse_product")
@Getter
@EntityListeners(JpaListener.class)
public class JpaWarehouseProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Setter
    String name;

    @Setter
    Integer quantity;

    @Override
    public String toString() {
        return "JpaProduct{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
