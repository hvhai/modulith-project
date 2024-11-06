package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.eventsourcing.EventSourcingService;
import com.codehunter.modulithproject.eventsourcing.OrderEvent;
import com.codehunter.modulithproject.eventsourcing.PaymentEvent;
import com.codehunter.modulithproject.eventsourcing.WarehouseEvent;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderPayment;
import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderPaymentRepository;
import com.codehunter.modulithproject.order.jpa_repository.OrderProductRepository;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.order.mapper.OrderMapper;
import com.codehunter.modulithproject.payment.PaymentCreatedEvent;
import com.codehunter.modulithproject.payment.PaymentPurchasedEvent;
import com.codehunter.modulithproject.warehouse.WarehouseProductCreateEvent;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class OrderModuleEventHandler {
    private final OrderProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final EventSourcingService eventSourcingService;
    private final OrderMapper orderMapper;

    public OrderModuleEventHandler(OrderProductRepository productRepository, OrderRepository orderRepository, OrderPaymentRepository orderPaymentRepository,
                                   EventSourcingService eventSourcingService, OrderMapper orderMapper) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderPaymentRepository = orderPaymentRepository;
        this.eventSourcingService = eventSourcingService;
        this.orderMapper = orderMapper;
    }

    void onWarehouseProductCreateEvent(WarehouseProductCreateEvent event) {
        log.info("On WarehouseProductCreateEvent, Product id={}, name={}, price={}", event.id(), event.name(), event.price());
        JpaOrderProduct product = new JpaOrderProduct();
        product.setName(event.name());
        product.setId(event.id());
        product.setPrice(event.price());
        productRepository.save(product);
    }

    void onWarehouseProductPackageCompletedEvent(WarehouseService.WarehouseProductPackageCompletedEvent event) {
        String orderId = event.orderId();
        log.info("On WarehouseProductPackageCompletedEvent, Order orderId={}", orderId);
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        JpaOrder updatedOrder = orderRepository.save(order.registerForPayment());
//        paymentService.createPayment(new PaymentService.CreatePaymentRequest(orderId, updatedOrder.getTotalAmount()));
        eventSourcingService.addOrderEvent(new OrderEvent(orderMapper.toOrderDTO(updatedOrder), OrderEvent.OrderEventType.IN_PAYMENT));
    }

    void onWarehouseProductOutOfStockEvent(WarehouseService.WarehouseProductOutOfStockEvent event) {
        String orderId = event.orderId();
        log.info("On WarehouseProductOutOfStockEvent, Order orderId={}", orderId);
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        orderRepository.save(order.cancel());
        log.info("On WarehouseProductOutOfStockEvent, Order orderId={} change status to CANCELED", orderId);
    }

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
        orderRepository.save(order.waitingForPayment(jpaOrderPayment));
        log.info("On PaymentCreatedEvent, Order orderId={} change status to WAITING_FOR_PAYMENT", orderId);
    }

    void onPaymentPurchasedEvent(PaymentPurchasedEvent event) {
        String orderId = event.payment().orderId();
        log.info("On PaymentPurchasedEvent, Order orderId={}", orderId);
        Optional<JpaOrder> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.error("Order with orderId={} not found", orderId);
            return;
        }
        JpaOrder order = orderOptional.get();
        orderRepository.save(order.finish());
        log.info("On PaymentPurchasedEvent, Order orderId={} change status to DONE", orderId);
    }

    @ApplicationModuleListener
    public void onWarehouseEvent(WarehouseEvent warehouseEvent) {
        log.info("[Order]Consume Warehouse event {}", warehouseEvent.warehouseEventType());
        switch (warehouseEvent.warehouseEventType()) {
            case OUT_OF_STOCK_PRODUCT:
                onWarehouseProductOutOfStockEvent(new WarehouseService.WarehouseProductOutOfStockEvent(warehouseEvent.orderId(), warehouseEvent.products().getFirst()));
                break;
            case RESERVE_PRODUCT_COMPLETED:
                onWarehouseProductPackageCompletedEvent(new WarehouseService.WarehouseProductPackageCompletedEvent(warehouseEvent.orderId()));
                break;
            case ADD_PRODUCT:
                onWarehouseProductCreateEvent(new WarehouseProductCreateEvent(warehouseEvent.products().getFirst().id(), warehouseEvent.products().getFirst().name(), warehouseEvent.products().getFirst().price()));
                break;
        }

    }

    @ApplicationModuleListener
    public void onPaymentEvent(PaymentEvent paymentEvent) {
        log.info("[Order]Consume Payment event {}", paymentEvent.paymentEventType());
        switch (paymentEvent.paymentEventType()) {
            case CREATED:
                onPaymentCreatedEvent(new PaymentCreatedEvent(paymentEvent.payment()));
                break;
            case PURCHASED:
                onPaymentPurchasedEvent(new PaymentPurchasedEvent(paymentEvent.payment()));
                break;
        }


    }
}
