package com.codehunter.modulithproject.warehouse;

import java.util.List;

public record GetProductForOrderEvent(String orderId, List<Product> order) {
}
