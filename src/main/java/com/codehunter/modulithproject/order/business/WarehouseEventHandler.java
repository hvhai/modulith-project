package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.order.OrderStatus;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderProductRepository;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.warehouse.WarehouseProductCreateEvent;
import com.codehunter.modulithproject.warehouse.WarehouseServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@Slf4j
public class WarehouseEventHandler {
    private final OrderProductRepository productRepository;
    private final OrderRepository orderRepository;

    public WarehouseEventHandler(OrderProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @ApplicationModuleListener
    void onWarehouseProductCreateEvent(WarehouseProductCreateEvent event) {
        JpaOrderProduct product = new JpaOrderProduct();
        product.setName(event.name());
        product.setId(event.id());
        productRepository.save(product);
    }

    @ApplicationModuleListener
    void onWarehouseProductPackageCompletedEvent(WarehouseServiceApi.WarehouseProductPackageCompletedEvent event) {
        String orderId = event.orderId();
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        order.setOrderStatus(OrderStatus.IN_PAYMENT);
        orderRepository.save(order);
        log.info("On WarehouseProductPackageCompletedEvent, Order orderId={} change status to IN_PAYMENT", orderId);
    }

    @ApplicationModuleListener
    void onWarehouseProductOutOfStockEvent(WarehouseServiceApi.WarehouseProductOutOfStockEvent event) {
        String orderId = event.orderId();
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        log.info("On WarehouseProductPackageCompletedEvent, Order orderId={} change status to CANCELED", orderId);
    }
}
