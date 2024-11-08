package com.codehunter.modulithproject.eventsourcing;

import java.util.List;

public record WarehouseEvent(List<ProductDTO> products, String orderId, WarehouseEventType warehouseEventType) {
    public enum WarehouseEventType {
        ADDED,
        RESERVE_COMPLETED,
        OUT_OF_STOCK,
    }

}
