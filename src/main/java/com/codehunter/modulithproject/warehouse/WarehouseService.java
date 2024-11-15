package com.codehunter.modulithproject.warehouse;

import com.codehunter.modulithproject.eventsourcing.OrderDTO;
import com.codehunter.modulithproject.eventsourcing.ProductDTO;

import java.util.List;

public interface WarehouseService {
    void reserveProductForOrder(OrderDTO request);

    List<WarehouseProductDTO> getAllProduct();

    ProductDTO getProduct(String id);

}
