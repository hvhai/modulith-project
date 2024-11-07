package com.codehunter.modulithproject.warehouse;

import com.codehunter.modulithproject.eventsourcing.OrderDTO;
import com.codehunter.modulithproject.eventsourcing.ProductDTO;

import java.util.List;
import java.util.Set;

public interface WarehouseService {
    record ReserveProductForOrderRequest(String orderId, Set<ProductDTO> products) {
    }

    record WarehouseProductOutOfStockEvent(String orderId, ProductDTO product) {
    }

    record WarehouseProductPackageCompletedEvent(String orderId) {
    }

    void reserveProductForOrder(OrderDTO request);

    List<WarehouseProductDTO> getAllProduct();

    ProductDTO getProduct(String id);

}
