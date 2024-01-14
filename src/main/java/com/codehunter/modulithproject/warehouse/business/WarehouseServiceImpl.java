package com.codehunter.modulithproject.warehouse.business;

import com.codehunter.modulithproject.warehouse.GetProductForOrderEvent;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    @ApplicationModuleListener
    @Override
    public void onGetProductForOrderEvent(GetProductForOrderEvent getProductForOrderRequest) {
        log.info("onGetProductForOrderEvent");
    }
}
