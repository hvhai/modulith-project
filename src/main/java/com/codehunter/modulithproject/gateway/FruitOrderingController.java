package com.codehunter.modulithproject.gateway;

import com.codehunter.modulithproject.gateway.mapper.UserMapper;
import com.codehunter.modulithproject.gateway.response.ResponseDTO;
import com.codehunter.modulithproject.gateway.response.ResponseFormatter;
import com.codehunter.modulithproject.gateway.util.AuthenticationUtil;
import com.codehunter.modulithproject.order.OrderDTO;
import com.codehunter.modulithproject.order.OrderService;
import com.codehunter.modulithproject.warehouse.WarehouseProductDTO;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/fruit-ordering")
@Slf4j
public class FruitOrderingController {
    private final OrderService orderService;
    private final WarehouseService warehouseService;

    public FruitOrderingController(OrderService orderService, WarehouseService warehouseService) {
        this.orderService = orderService;
        this.warehouseService = warehouseService;
    }

    @PostMapping("/orders")
    ResponseEntity<ResponseDTO<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("POST createOrder");
        UserDTO user = AuthenticationUtil.getUser();
        OrderDTO order = orderService.createOrder(orderDTO, UserMapper.toFruitOrderingUserDTO(user));
        return ResponseFormatter.handleSingle(order, new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/products")
    ResponseEntity<ResponseDTO<List<WarehouseProductDTO>>> getAllProduct() {
        log.info("GET getAllProduct");
        List<WarehouseProductDTO> allProduct = warehouseService.getAllProduct();
        return ResponseFormatter.handleList(allProduct);
    }

    @GetMapping("/orders")
    ResponseEntity<ResponseDTO<List<OrderDTO>>> getAllOrders() {
        log.info("GET getAllOrders");
        List<OrderDTO> allOrders = orderService.getAllOrders();
        return ResponseFormatter.handleList(allOrders);
    }
}
