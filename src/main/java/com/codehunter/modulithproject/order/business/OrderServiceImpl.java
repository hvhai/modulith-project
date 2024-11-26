package com.codehunter.modulithproject.order.business;

import com.codehunter.modulithproject.eventsourcing.EventSourcingService;
import com.codehunter.modulithproject.order.OrderService;
import com.codehunter.modulithproject.order.UserDTO;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderProductRepository;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.order.mapper.OrderMapper;
import com.codehunter.modulithproject.shared.IdNotFoundException;
import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.eventsourcing.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderMapper orderMapper;
    private final EventSourcingService eventSourcingService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderProductRepository orderProductRepository, OrderMapper orderMapper, EventSourcingService eventSourcingService) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.orderMapper = orderMapper;
        this.eventSourcingService = eventSourcingService;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO createOrderRequest, UserDTO user) {
        JpaOrder newOrder = createJpaOrder(createOrderRequest);
        OrderDTO result = orderMapper.toOrderDTO(newOrder);
        eventSourcingService.addOrderEvent(new OrderEvent(result, OrderEvent.OrderEventType.CREATED));
        return result;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<JpaOrder> allOrders = orderRepository.findAll();
        return orderMapper.toOrderDTO(allOrders);
    }

    @Override
    public OrderDTO getOrder(String id) {
        Optional<JpaOrder> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            log.warn("Order not found, id={}", id);
            throw new IdNotFoundException(String.format("Order not found, id=%s", id));
        }
        return orderMapper.toOrderDTO(orderOptional.get());
    }

    @Transactional
    JpaOrder createJpaOrder(OrderDTO createOrderRequest) {
        Set<JpaOrderProduct> products = createOrderRequest.products().stream()
                .map(productDTO -> {
                    Optional<JpaOrderProduct> product = orderProductRepository.findById(productDTO.id());
                    if (product.isPresent()) {
                        return product.get();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        JpaOrder order = new JpaOrder(products);
        JpaOrder newOrder = orderRepository.save(order);
        return newOrder;
    }
}
