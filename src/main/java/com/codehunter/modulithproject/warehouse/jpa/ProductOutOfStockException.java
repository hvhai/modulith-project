package com.codehunter.modulithproject.warehouse.jpa;


public class ProductOutOfStockException extends RuntimeException {
    private final JpaWarehouseProduct product;

    public ProductOutOfStockException(String message, JpaWarehouseProduct product) {
        super(message);
        this.product = product;
    }

    public JpaWarehouseProduct getProduct() {
        return product;
    }
}
