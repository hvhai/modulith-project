package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.order.OrderStatus;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderProductRepository;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.payment.PaymentPurchasedEvent;
import com.codehunter.modulithproject.payment.PaymentService;
import com.codehunter.modulithproject.warehouse.WarehouseProductCreateEvent;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Transactional
@Slf4j
public class OrderEventHandler {
    private final OrderProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    public OrderEventHandler(OrderProductRepository productRepository, OrderRepository orderRepository, PaymentService paymentService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    @ApplicationModuleListener
    void onWarehouseProductCreateEvent(WarehouseProductCreateEvent event) {
        JpaOrderProduct product = new JpaOrderProduct();
        product.setName(event.name());
        product.setId(event.id());
        product.setPrice(event.price());
        productRepository.save(product);
    }

    @ApplicationModuleListener
    void onWarehouseProductPackageCompletedEvent(WarehouseService.WarehouseProductPackageCompletedEvent event) {
        String orderId = event.orderId();
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        order.setOrderStatus(OrderStatus.IN_PAYMENT);
        BigDecimal totalAmount = order.getProducts().stream()
                .map(JpaOrderProduct::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        paymentService.createPayment(new PaymentService.CreatePaymentRequest(orderId, totalAmount));
        log.info("On WarehouseProductPackageCompletedEvent, Order orderId={} change status to IN_PAYMENT", orderId);
    }

    @ApplicationModuleListener
    void onWarehouseProductOutOfStockEvent(WarehouseService.WarehouseProductOutOfStockEvent event) {
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

    @ApplicationModuleListener
    void onWarehouseProductOutOfStockEvent(PaymentPurchasedEvent event) {
        String orderId = event.orderId();
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        order.setPaymentId(event.id());
        order.setOrderStatus(OrderStatus.DONE);
        orderRepository.save(order);
        log.info("On PaymentPurchasedEvent, Order orderId={} change status to DONE", orderId);
    }
}
