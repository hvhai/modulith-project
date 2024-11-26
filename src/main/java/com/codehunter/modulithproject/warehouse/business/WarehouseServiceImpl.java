package com.codehunter.modulithproject.warehouse.business;

import com.codehunter.modulithproject.eventsourcing.EventSourcingService;
import com.codehunter.modulithproject.shared.IdNotFoundException;
import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.shared.ProductDTO;
import com.codehunter.modulithproject.eventsourcing.WarehouseEvent;
import com.codehunter.modulithproject.warehouse.WarehouseProductDTO;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa.ProductOutOfStockException;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import com.codehunter.modulithproject.warehouse.mapper.WarehouseProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseProductRepository productRepository;
    private final WarehouseProductMapper warehouseProductMapper;
    private final EventSourcingService eventSourcingService;

    public WarehouseServiceImpl(WarehouseProductRepository productRepository, WarehouseProductMapper warehouseProductMapper, EventSourcingService eventSourcingService) {
        this.productRepository = productRepository;
        this.warehouseProductMapper = warehouseProductMapper;
        this.eventSourcingService = eventSourcingService;
    }

    @Override
    @Transactional
    public void reserveProductForOrder(OrderDTO request) {
        String orderId = request.id();
        log.info("Reserve product for OrderId={}", request.id());
        Map<String, ProductDTO> productMap = request.products().stream()
                .collect(Collectors.toMap(ProductDTO::id, Function.identity()));
        List<JpaWarehouseProduct> existentProductList = productRepository.findAllById(productMap.keySet());

        try {
            existentProductList.forEach(JpaWarehouseProduct::reserveForOrder);
        } catch (ProductOutOfStockException exception) {
            log.info("[WarehouseProductOutOfStockEvent]Products are out of stock for OrderId={}", orderId);
//            applicationEventPublisher.publishEvent(new WarehouseProductOutOfStockEvent(request.orderId(), warehouseProductMapper.toProductDto(exception.getProduct())));
            eventSourcingService.addWarehouseEvent(
                    new WarehouseEvent(List.of(
                            warehouseProductMapper.toProductDto(exception.getProduct())),
                            request.id(),
                            WarehouseEvent.WarehouseEventType.OUT_OF_STOCK));
            return;
        }
        productRepository.saveAll(existentProductList);
        log.info("[WarehouseProductPackageCompletedEvent]Products are ready for OrderId={}", orderId);
//        applicationEventPublisher.publishEvent(new WarehouseProductPackageCompletedEvent(orderId));
        eventSourcingService.addWarehouseEvent(
                new WarehouseEvent(
                        Collections.EMPTY_LIST,
                        request.id(),
                        WarehouseEvent.WarehouseEventType.RESERVE_COMPLETED));
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
