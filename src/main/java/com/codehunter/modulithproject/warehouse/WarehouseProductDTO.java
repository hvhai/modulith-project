package com.codehunter.modulithproject.warehouse;

import java.math.BigDecimal;

public record WarehouseProductDTO(String id, String name, Integer quantity, BigDecimal price) {
}
