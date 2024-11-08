package com.codehunter.modulithproject.warehouse.init;

import com.codehunter.modulithproject.eventsourcing.EventSourcingService;
import com.codehunter.modulithproject.eventsourcing.WarehouseEvent;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import com.codehunter.modulithproject.warehouse.mapper.WarehouseProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@Transactional
public class InitData implements ApplicationListener<ContextRefreshedEvent> {
    private final WarehouseProductRepository productRepository;
    private final ApplicationEventPublisher publisher;
    private final EventSourcingService eventSourcingService;
    private final WarehouseProductMapper warehouseProductMapper;

    public InitData(WarehouseProductRepository productRepository, ApplicationEventPublisher publisher, EventSourcingService eventSourcingService, WarehouseProductMapper warehouseProductMapper) {
        this.productRepository = productRepository;
        this.publisher = publisher;
        this.eventSourcingService = eventSourcingService;
        this.warehouseProductMapper = warehouseProductMapper;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("onApplicationEvent: App STARTED");
        JpaWarehouseProduct product1 = new JpaWarehouseProduct();
        product1.setName("Apple");
        product1.setQuantity(10);
        product1.setPrice(new BigDecimal(10_000));
        JpaWarehouseProduct product2 = new JpaWarehouseProduct();
        product2.setName("Orange");
        product2.setQuantity(0);
        product2.setPrice(new BigDecimal(5_000));
        JpaWarehouseProduct product3 = new JpaWarehouseProduct();
        product3.setName("Lemon");
        product3.setQuantity(5);
        product3.setPrice(new BigDecimal(2_000));
        List<JpaWarehouseProduct> jpaProducts = productRepository.saveAll(List.of(product1, product2, product3));
        jpaProducts.stream()
                .forEach(product ->
                        eventSourcingService.addWarehouseEvent(
                                new WarehouseEvent(List.of(warehouseProductMapper.toProductDto(product)), product.getId(), WarehouseEvent.WarehouseEventType.ADDED)));
    }
}
