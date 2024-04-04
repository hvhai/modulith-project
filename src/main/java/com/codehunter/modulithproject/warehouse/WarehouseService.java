package com.codehunter.modulithproject.warehouse;

import com.codehunter.modulithproject.order.GetProductForOrderEvent;

public interface WarehouseService {
    void onGetProductForOrderEvent(GetProductForOrderEvent getProductForOrderRequest);

}
