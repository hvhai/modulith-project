package com.codehunter.modulithproject.order;


import java.util.List;

public record GetProductForOrderEvent(String orderId, List<ProductDTO> order) {
}
