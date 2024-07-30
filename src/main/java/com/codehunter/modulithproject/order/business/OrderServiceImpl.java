package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.order.OrderDTO;
import com.codehunter.modulithproject.order.OrderService;
import com.codehunter.modulithproject.order.OrderStatus;
import com.codehunter.modulithproject.order.UserDTO;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderProductRepository;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.order.mapper.OrderMapper;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final WarehouseService warehouseService;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(WarehouseService warehouseService, OrderRepository orderRepository,
                            OrderProductRepository orderProductRepository, OrderMapper orderMapper) {
        this.warehouseService = warehouseService;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO createOrder(OrderDTO createOrderRequest, UserDTO user) {
        JpaOrder newOrder = createJpaOrder(createOrderRequest);
        OrderDTO result = orderMapper.toOrderDTO(newOrder);

        warehouseService.reserveProductForOrder(
                new WarehouseService.ReserveProductForOrderRequest(result.id(), result.productList()));
        log.info("Order created with id={}, status={}", result.id(), result.orderStatus());
        return result;
    }

    @Transactional
    JpaOrder createJpaOrder(OrderDTO createOrderRequest) {
        JpaOrder order = new JpaOrder();
        order.setOrderStatus(OrderStatus.IN_PRODUCT_PREPARE);

        List<JpaOrderProduct> productList = createOrderRequest.productList().stream()
                .map(productDTO -> {
                    Optional<JpaOrderProduct> product = orderProductRepository.findById(productDTO.id());
                    if (product.isPresent()) {
                        return product.get();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        order.setProductList(productList);
        JpaOrder newOrder = orderRepository.save(order);
        return newOrder;
    }
}
