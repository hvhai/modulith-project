package com.codehunter.modulithproject.warehouse.jpa;

import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JpaListener {

    public JpaListener() {
    }

    @PostPersist
    private void afterCreate(JpaWarehouseProduct product) {
        log.info("[PostPersist] JpaWarehouseProduct create product {}", product);
    }
}
