package com.codehunter.modulithproject.warehouse;

import com.codehunter.modulithproject.shared.ProductDTO;

import java.util.List;

public interface WarehouseService {
    record ReserveProductForOrderRequest(String orderId, List<ProductDTO> productList) {
    }

    record WarehouseProductOutOfStockEvent(String orderId, List<ProductDTO> productList) {
    }

    record WarehouseProductPackageCompletedEvent(String orderId) {
    }

    void reserveProductForOrder(ReserveProductForOrderRequest request);
}
