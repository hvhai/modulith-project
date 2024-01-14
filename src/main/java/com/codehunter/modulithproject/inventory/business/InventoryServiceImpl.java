package com.codehunter.modulithproject.inventory.business;

import com.codehunter.modulithproject.inventory.GetProductForOrderEvent;
import com.codehunter.modulithproject.inventory.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    @ApplicationModuleListener
    @Override
    public void onGetProductForOrderEvent(GetProductForOrderEvent getProductForOrderRequest) {
        log.info("onGetProductForOrderEvent");
    }
}
