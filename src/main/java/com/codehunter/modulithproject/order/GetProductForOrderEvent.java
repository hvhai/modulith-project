package com.codehunter.modulithproject.order;


import com.codehunter.modulithproject.shared.ProductDTO;

import java.util.List;

public record GetProductForOrderEvent(String orderId, List<ProductDTO> order) {
}
