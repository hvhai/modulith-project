package com.codehunter.modulithproject.warehouse.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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

    @Setter
    BigDecimal price;

    @Override
    public String toString() {
        return "JpaWarehouseProduct{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", quantity=" + quantity +
               ", price=" + price +
               '}';
    }

    public JpaWarehouseProduct() {
    }

    public JpaWarehouseProduct(String name, Integer quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public JpaWarehouseProduct reserveForOrder() {
        if (this.quantity < 1) {
            throw new ProductOutOfStockException("Not enough quantity to reserve " + this, this);
        }
        this.quantity -= 1;
        return this;
    }
}
