package com.codehunter.modulithproject.warehouse.business;

import com.codehunter.modulithproject.shared.ProductDTO;
import com.codehunter.modulithproject.warehouse.WarehouseServiceApi;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WarehouseServiceImpl implements WarehouseServiceApi {
    private final WarehouseProductRepository productRepository;

    public WarehouseServiceImpl(WarehouseProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void reserveProductForOrder(ReserveProductForOrderRequest request) {
        Map<String, ProductDTO> productMap = request.productList().stream()
                .collect(Collectors.toMap(ProductDTO::id, Function.identity()));
        List<JpaWarehouseProduct> existentProductList = productRepository.findAllById(productMap.keySet());
        List<JpaWarehouseProduct> outOfStockList = existentProductList.stream()
                .filter(existentProduct -> existentProduct.getQuantity() <= 0)
                .toList();

        if (CollectionUtils.isNotEmpty(outOfStockList)) {
            outOfStockList.forEach(
                    outOfStockProduct -> log.info("OrderId={} , productId={} out of stock", request.orderId(), outOfStockProduct));
            return;
        }

        existentProductList.forEach(product -> {
            product.setQuantity(product.getQuantity() - 1);
            productRepository.save(product);
        });
        log.info("Products are ready for OrderId={}", request.orderId());
    }
}
