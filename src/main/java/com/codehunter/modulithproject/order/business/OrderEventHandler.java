package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.order.OrderStatus;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderPayment;
import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderProductRepository;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.order.mapper.OrderPaymentMapper;
import com.codehunter.modulithproject.payment.PaymentCreatedEvent;
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
@Slf4j
public class OrderEventHandler {
    private final OrderProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final OrderPaymentMapper orderPaymentMapper;

    public OrderEventHandler(OrderProductRepository productRepository, OrderRepository orderRepository,
                             PaymentService paymentService, OrderPaymentMapper orderPaymentMapper) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.orderPaymentMapper = orderPaymentMapper;
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
        log.info("On WarehouseProductPackageCompletedEvent, Order orderId={}", orderId);
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
        log.info("On WarehouseProductOutOfStockEvent, Order orderId={}", orderId);
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
    void onPaymentCreatedEvent(PaymentCreatedEvent event) {
        String orderId = event.payment().orderId();
        log.info("On PaymentCreatedEvent, Order orderId={}", orderId);
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        JpaOrderPayment jpaOrderPayment = orderPaymentMapper.toJpaOrderPayment(event.payment());
        order.setPayment(jpaOrderPayment);
        order.setOrderStatus(OrderStatus.WAITING_FOR_PAYMENT);
        orderRepository.save(order);
        log.info("On PaymentCreatedEvent, Order orderId={} change status to WAITING_FOR_PAYMENT", orderId);
    }

    @ApplicationModuleListener
    void onPaymentPurchasedEvent(PaymentPurchasedEvent event) {
        String orderId = event.payment().orderId();
        log.info("On PaymentPurchasedEvent, Order orderId={}", orderId);
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        order.setPayment(orderPaymentMapper.toJpaOrderPayment(event.payment()));
        order.setOrderStatus(OrderStatus.DONE);
        orderRepository.save(order);
        log.info("On PaymentPurchasedEvent, Order orderId={} change status to DONE", orderId);
    }
}
