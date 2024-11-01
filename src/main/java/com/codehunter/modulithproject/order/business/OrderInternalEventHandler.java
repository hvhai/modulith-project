package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.mapper.OrderMapper;
import com.codehunter.modulithproject.order.mapper.OrderProductMapper;
import com.codehunter.modulithproject.payment.PaymentService;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

@Component
@Slf4j
public class OrderInternalEventHandler {
    private final WarehouseService warehouseService;
    private final OrderProductMapper orderProductMapper;
    private final OrderMapper orderMapper;
    private final PaymentService paymentService;

    public OrderInternalEventHandler(WarehouseService warehouseService, OrderProductMapper orderProductMapper, OrderMapper orderMapper, PaymentService paymentService) {
        this.warehouseService = warehouseService;
        this.orderProductMapper = orderProductMapper;
        this.orderMapper = orderMapper;
        this.paymentService = paymentService;
    }

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void handleOrderCreatedEvent(JpaOrder.OrderCreatedEvent event) {
        log.info("Handle order created with id={}", event.order().getId());
        warehouseService.reserveProductForOrder(
                new WarehouseService.ReserveProductForOrderRequest(event.order().getId(), orderProductMapper.toProductDTO(event.order().getProducts())));
    }

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void handleOrderInRegisterPaymentEvent(JpaOrder.OrderInRegisterPaymentEvent event) {
        log.info("Handle order in payment with id={}", event.order().getId());
        paymentService.createPayment(new PaymentService.CreatePaymentRequest(event.order().getId(), event.order().getTotalAmount()));
    }
}
