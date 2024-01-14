package com.codehunter.modulithproject.gateway;

import com.codehunter.modulithproject.gateway.mapper.UserMapper;
import com.codehunter.modulithproject.gateway.response.ResponseDTO;
import com.codehunter.modulithproject.gateway.response.ResponseFormatter;
import com.codehunter.modulithproject.gateway.util.AuthenticationUtil;
import com.codehunter.modulithproject.order.OrderDTO;
import com.codehunter.modulithproject.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/fruit-ordering")
@Slf4j
public class FruitOrderingController {
    private final OrderService orderService;

    public FruitOrderingController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    ResponseEntity<ResponseDTO<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("POST createOrder");
        UserDTO user = AuthenticationUtil.getUser();
        OrderDTO order = orderService.createOrder(orderDTO, UserMapper.toFruitOrderingUserDTO(user));
        return ResponseFormatter.handleSingle(order, new HttpHeaders(), HttpStatus.CREATED);
    }
}
