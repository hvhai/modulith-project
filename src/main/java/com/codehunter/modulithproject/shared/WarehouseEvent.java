package com.codehunter.modulithproject.shared;

import java.util.List;

public record WarehouseEvent(List<ProductDTO> products, String orderId, WarehouseEventType warehouseEventType) {
    public enum WarehouseEventType {
        ADDED,
        RESERVE_COMPLETED,
        OUT_OF_STOCK,
    }

}
