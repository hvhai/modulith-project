package com.codehunter.modulithproject.warehouse.business;

import com.codehunter.modulithproject.shared.IdNotFoundException;
import com.codehunter.modulithproject.warehouse.ProductDTO;
import com.codehunter.modulithproject.warehouse.WarehouseProductDTO;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import com.codehunter.modulithproject.warehouse.mapper.WarehouseProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseProductRepository productRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final WarehouseProductMapper warehouseProductMapper;

    public WarehouseServiceImpl(WarehouseProductRepository productRepository, ApplicationEventPublisher applicationEventPublisher, WarehouseProductMapper warehouseProductMapper) {
        this.productRepository = productRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.warehouseProductMapper = warehouseProductMapper;
    }

    @Override
    @Transactional
    public void reserveProductForOrder(ReserveProductForOrderRequest request) {
        Map<String, ProductDTO> productMap = request.products().stream()
                .collect(Collectors.toMap(ProductDTO::id, Function.identity()));
        List<JpaWarehouseProduct> existentProductList = productRepository.findAllById(productMap.keySet());
        Set<JpaWarehouseProduct> outOfStockList = existentProductList.stream()
                .filter(existentProduct -> existentProduct.getQuantity() <= 0)
                .collect(Collectors.toSet());

        String orderId = request.orderId();
        if (CollectionUtils.isNotEmpty(outOfStockList)) {
            outOfStockList.forEach(
                    outOfStockProduct -> log.info("OrderId={} , productId={} out of stock", orderId, outOfStockProduct));
            applicationEventPublisher.publishEvent(new WarehouseProductOutOfStockEvent(orderId, warehouseProductMapper.toProductDto(outOfStockList)));
            return;
        }

        existentProductList.forEach(product -> {
            product.setQuantity(product.getQuantity() - 1);
            productRepository.save(product);
        });
        applicationEventPublisher.publishEvent(new WarehouseProductPackageCompletedEvent(orderId));
        log.info("Products are ready for OrderId={}", orderId);
    }

    @Override
    public List<WarehouseProductDTO> getAllProduct() {
        List<JpaWarehouseProduct> allJpaProduct = productRepository.findAll();
        return warehouseProductMapper.toWarehouseProductDto(allJpaProduct);
    }

    @Override
    public ProductDTO getProduct(String id) {
        Optional<JpaWarehouseProduct> jpaWarehouseProduct = productRepository.findById(id);
        if (jpaWarehouseProduct.isEmpty()) {
            log.warn("Product not found, id={}", id);
            throw new IdNotFoundException(String.format("Product not found, id=%s", id));
        }
        return warehouseProductMapper.toProductDto(jpaWarehouseProduct.get());
    }
}
