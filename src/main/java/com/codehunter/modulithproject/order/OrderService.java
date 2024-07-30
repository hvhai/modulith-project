package com.codehunter.modulithproject.order;

public interface OrderService {
    OrderDTO createOrder(OrderDTO createOrderRequest, UserDTO user);
}
