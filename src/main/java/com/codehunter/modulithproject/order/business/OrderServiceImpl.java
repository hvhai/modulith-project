package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.order.OrderDTO;
import com.codehunter.modulithproject.order.OrderService;
import com.codehunter.modulithproject.order.OrderStatus;
import com.codehunter.modulithproject.order.UserDTO;
import com.codehunter.modulithproject.warehouse.WarehouseServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final WarehouseServiceApi warehouseServiceApi;

    public OrderServiceImpl(WarehouseServiceApi warehouseServiceApi) {
        this.warehouseServiceApi = warehouseServiceApi;
    }

    @Override
    public OrderDTO createOrder(OrderDTO createOrderRequest, UserDTO user) {
        String id = UUID.randomUUID().toString();
        warehouseServiceApi.reserveProductForOrder(
                new WarehouseServiceApi.ReserveProductForOrderRequest(
                        UUID.randomUUID().toString(),
                        createOrderRequest.productList()));
        log.info("Complete Order");
        return new OrderDTO(id, OrderStatus.IN_PRODUCT_PREPARE, createOrderRequest.productList());
    }
}
