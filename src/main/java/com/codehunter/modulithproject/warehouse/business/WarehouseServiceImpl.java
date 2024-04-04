package com.codehunter.modulithproject.warehouse.business;

import com.codehunter.modulithproject.order.GetProductForOrderEvent;
import com.codehunter.modulithproject.order.ProductDTO;
import com.codehunter.modulithproject.warehouse.Product;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseProductRepository productRepository;

    public WarehouseServiceImpl(WarehouseProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ApplicationModuleListener
    @Override
    public void onGetProductForOrderEvent(GetProductForOrderEvent orderEvent) {
        log.info("onGetProductForOrderEvent");
        Map<String, ProductDTO> productMap = orderEvent.order().stream()
                .collect(Collectors.toMap(ProductDTO::id, Function.identity()));
        List<JpaWarehouseProduct> existentProductList = productRepository.findAllById(productMap.keySet());
        List<JpaWarehouseProduct> outOfStockList = existentProductList.stream()
                .filter(existentProduct -> existentProduct.getQuantity() <= 0)
                .toList();
        if (CollectionUtils.isEmpty(outOfStockList)) {
            log.info("Order ready");
        } else {

            outOfStockList.forEach(outOfStockProduct -> log.info("{} out of stock", outOfStockProduct));
        }
    }
}
