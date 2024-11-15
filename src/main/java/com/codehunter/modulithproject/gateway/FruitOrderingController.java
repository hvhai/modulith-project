package com.codehunter.modulithproject.gateway;

import com.codehunter.modulithproject.gateway.mapper.UserMapper;
import com.codehunter.modulithproject.gateway.response.ResponseDTO;
import com.codehunter.modulithproject.gateway.response.ResponseFormatter;
import com.codehunter.modulithproject.gateway.util.AuthenticationUtil;
import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.order.OrderService;
import com.codehunter.modulithproject.shared.PaymentDTO;
import com.codehunter.modulithproject.payment.PaymentService;
import com.codehunter.modulithproject.shared.ProductDTO;
import com.codehunter.modulithproject.shared.WarehouseProductDTO;
import com.codehunter.modulithproject.warehouse.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final PaymentService paymentService;

    public FruitOrderingController(OrderService orderService, WarehouseService warehouseService, PaymentService paymentService) {
        this.orderService = orderService;
        this.warehouseService = warehouseService;
        this.paymentService = paymentService;
    }

    @GetMapping("/orders")
    ResponseEntity<ResponseDTO<List<OrderDTO>>> getAllOrders() {
        log.info("GET getAllOrders");
        List<OrderDTO> allOrders = orderService.getAllOrders();
        return ResponseFormatter.handleList(allOrders);
    }

    @PostMapping("/orders")
    ResponseEntity<ResponseDTO<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        log.info("POST createOrder");
        UserDTO user = AuthenticationUtil.getUser();
        OrderDTO order = orderService.createOrder(orderDTO, UserMapper.toFruitOrderingUserDTO(user));
        return ResponseFormatter.handleSingle(order, new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<ResponseDTO<OrderDTO>> getOrderInfo(@PathVariable String id) {
        log.info("GET getOrderInfo");
        OrderDTO order = orderService.getOrder(id);
        return ResponseFormatter.handleSingle(order, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/products")
    ResponseEntity<ResponseDTO<List<WarehouseProductDTO>>> getAllProduct() {
        log.info("GET getAllProduct");
        List<WarehouseProductDTO> allProduct = warehouseService.getAllProduct();
        return ResponseFormatter.handleList(allProduct);
    }

    @GetMapping("/products/{id}")
    ResponseEntity<ResponseDTO<ProductDTO>> getProductInfo(@PathVariable String id) {
        log.info("GET getProductInfo");
        ProductDTO product = warehouseService.getProduct(id);
        return ResponseFormatter.handleSingle(product, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/payments")
    ResponseEntity<ResponseDTO<List<PaymentDTO>>> getAllPayments() {
        log.info("GET getAllPayments");
        List<PaymentDTO> allPayments = paymentService.getAllPayments();
        return ResponseFormatter.handleList(allPayments);
    }

    @GetMapping("/payments/{id}")
    ResponseEntity<ResponseDTO<PaymentDTO>> getPaymentInfo(@PathVariable String id) {
        log.info("GET getPaymentInfo");
        PaymentDTO payment = paymentService.getPayment(id);
        return ResponseFormatter.handleSingle(payment, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/payments/{id}/purchase")
    ResponseEntity<ResponseDTO<PaymentDTO>> purchaseAPayment(@PathVariable String id) {
        log.info("POST purchaseAPayment");
        UserDTO user = AuthenticationUtil.getUser();
        PaymentDTO payment = paymentService.purchasePayment(id);
        return ResponseFormatter.handleSingle(payment, new HttpHeaders(), HttpStatus.CREATED);
    }
}
