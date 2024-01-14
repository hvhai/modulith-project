package com.codehunter.modulithproject.inventory;

import java.util.List;

public record GetProductForOrderEvent(String orderId, List<Product> order) {
}
