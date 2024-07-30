package com.codehunter.modulithproject.order;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO createOrderRequest, UserDTO user);

    List<OrderDTO> getAllOrders();
}
