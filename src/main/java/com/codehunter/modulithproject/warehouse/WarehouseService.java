package com.codehunter.modulithproject.warehouse;

import java.util.List;
import java.util.Set;

public interface WarehouseService {
    record ReserveProductForOrderRequest(String orderId, Set<ProductDTO> products) {
    }

    record WarehouseProductOutOfStockEvent(String orderId, Set<ProductDTO> products) {
    }

    record WarehouseProductPackageCompletedEvent(String orderId) {
    }

    void reserveProductForOrder(ReserveProductForOrderRequest request);

    List<WarehouseProductDTO> getAllProduct();
}
