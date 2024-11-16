package com.codehunter.modulithproject.warehouse.business;

import com.codehunter.modulithproject.shared.OrderEvent;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WarehouseEventHandler {
    private final WarehouseService warehouseService;

    public WarehouseEventHandler(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @ApplicationModuleListener
    public void onOrderEvent(OrderEvent orderEvent) {
        log.info("[Warehouse]Consume Order event {}", orderEvent.orderEventType());
        switch (orderEvent.orderEventType()) {
            case CREATED:
                warehouseService.reserveProductForOrder(orderEvent.order());
                break;
            case PAYMENT_REQUESTED, CANCELLED:
                log.info("Do nothing");
                break;
        }

    }
}
