package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderProductRepository;
import com.codehunter.modulithproject.warehouse.WarehouseProductCreateEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SyncEventListener {
    private final OrderProductRepository productRepository;

    public SyncEventListener(OrderProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ApplicationModuleListener
    void onWarehouseProductCreateEvent(WarehouseProductCreateEvent event) {
        JpaOrderProduct product = new JpaOrderProduct();
        product.setName(event.name());
        product.setId(event.id());
        productRepository.save(product);
    }
}
