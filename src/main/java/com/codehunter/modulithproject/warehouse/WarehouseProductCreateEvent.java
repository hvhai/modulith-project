package com.codehunter.modulithproject.warehouse;

import java.math.BigDecimal;

public record WarehouseProductCreateEvent(String id, String name, BigDecimal price) {
}
