package com.codehunter.modulithproject.warehouse.init;

import com.codehunter.modulithproject.warehouse.WarehouseProductCreateEvent;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@Transactional
public class InitData implements ApplicationListener<ContextRefreshedEvent> {
    private final WarehouseProductRepository productRepository;
    private final ApplicationEventPublisher publisher;

    public InitData(WarehouseProductRepository productRepository, ApplicationEventPublisher publisher) {
        this.productRepository = productRepository;
        this.publisher = publisher;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("onApplicationEvent: App STARTED");
        JpaWarehouseProduct product1 = new JpaWarehouseProduct();
        product1.setName("Apple");
        product1.setQuantity(10);
        JpaWarehouseProduct product2 = new JpaWarehouseProduct();
        product2.setName("Orange");
        product2.setQuantity(0);
        JpaWarehouseProduct product3 = new JpaWarehouseProduct();
        product3.setName("Lemon");
        product3.setQuantity(5);
        List<JpaWarehouseProduct> jpaProducts = productRepository.saveAll(List.of(product1, product2, product3));
        jpaProducts.stream().forEach(product ->
                publisher.publishEvent(
                        new WarehouseProductCreateEvent(product.getId(), product.getName())));
    }
}
