package com.codehunter.modulithproject.order;

import com.codehunter.modulithproject.eventsourcing.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO createOrderRequest, UserDTO user);

    List<OrderDTO> getAllOrders();

    OrderDTO getOrder(String id);
}
