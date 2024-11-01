package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.order.OrderStatus;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderPayment;
import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderPaymentRepository;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
@Slf4j
public class OrderModuleEventHandler {
    private final OrderProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final PaymentService paymentService;
    private final OrderPaymentMapper orderPaymentMapper;

    public OrderModuleEventHandler(OrderProductRepository productRepository, OrderRepository orderRepository, OrderPaymentRepository orderPaymentRepository,
                                   PaymentService paymentService, OrderPaymentMapper orderPaymentMapper) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderPaymentRepository = orderPaymentRepository;
        this.paymentService = paymentService;
        this.orderPaymentMapper = orderPaymentMapper;
    }

    @ApplicationModuleListener
    void onWarehouseProductCreateEvent(WarehouseProductCreateEvent event) {
        log.info("On WarehouseProductCreateEvent, Product id={}, name={}, price={}", event.id(), event.name(), event.price());
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
        orderRepository.save(order.registerForPayment());
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

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void onPaymentCreatedEvent(PaymentCreatedEvent event) {
        String orderId = event.payment().orderId();
        log.info("On PaymentCreatedEvent, Order orderId={}", orderId);
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        JpaOrderPayment jpaOrderPayment = orderPaymentRepository.save(new JpaOrderPayment(event.payment().id(), order, event.payment().totalAmount()));
        orderRepository.save(order.initPayment(jpaOrderPayment));
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
