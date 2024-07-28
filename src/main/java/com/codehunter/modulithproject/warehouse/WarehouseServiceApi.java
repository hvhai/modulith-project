package com.codehunter.modulithproject.warehouse;

import com.codehunter.modulithproject.shared.ProductDTO;

import java.util.List;

public interface WarehouseServiceApi {
    record ReserveProductForOrderRequest(String orderId, List<ProductDTO> order) {
    }

    void reserveProductForOrder(ReserveProductForOrderRequest request);
}
