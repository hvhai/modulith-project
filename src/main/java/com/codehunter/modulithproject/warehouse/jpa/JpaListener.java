package com.codehunter.modulithproject.warehouse.jpa;

import com.codehunter.modulithproject.warehouse.WarehouseProductCreateEvent;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
public class JpaListener {

    public JpaListener() {
    }

    @PostPersist
    private void afterCreate(JpaWarehouseProduct product) {
        log.info("[PostPersist] JpaWarehouseProduct create product {}", product);
    }
}
