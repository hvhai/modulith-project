package com.codehunter.modulithproject.gateway;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.codehunter.modulithproject.TestSecurityConfiguration;
import com.codehunter.modulithproject.eventsourcing.EventSourcingService;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.payment.jpa_repository.PaymentRepository;
import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.shared.OrderEvent;
import com.codehunter.modulithproject.shared.ProductDTO;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@ActiveProfiles("integration")
@Import(TestSecurityConfiguration.class)
@Sql(scripts = "classpath:datasource/schema-integration.sql")
class GatewayDirectDependenciesIntegrationTest {
    @Autowired
    private FruitOrderingController fruitOrderingController;

    @Autowired
    private WarehouseProductRepository warehouseProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @MockBean
    private EventSourcingService eventSourcingService;

    @Test
    @WithJwt(json = """
            {
              "sub": "user",
              "preferred_username": "user"
            }
            """)
    void createNewOrder(Scenario scenario) {
        // given
        // Stock: Apple: 10
        List<JpaWarehouseProduct> allProduct = warehouseProductRepository.findAll();
        assertThat(allProduct).hasSize(3);
        JpaWarehouseProduct warehouseProductDTO1 = allProduct.get(0);
        assertThat(warehouseProductDTO1.getName()).isEqualTo("Apple");
        assertThat(warehouseProductDTO1.getQuantity()).isEqualTo(10);

        doNothing().when(eventSourcingService).addOrderEvent(any(OrderEvent.class));

        // when
        ProductDTO productDTO = new ProductDTO(
                warehouseProductDTO1.getId(),
                warehouseProductDTO1.getName(),
                warehouseProductDTO1.getPrice()
        );
        OrderDTO orderDTO = new OrderDTO(
                null,
                null,
                null,
                null,
                Set.of(productDTO)
        );
        // when
        fruitOrderingController.createOrder(orderDTO);
        List<JpaOrder> allOrder = orderRepository.findAll();
        assertThat(allOrder).hasSize(1);
        String orderId = allOrder.get(0).getId();
//        JpaOrder createdOrder = orderRepository.findById(orderId).get();
//        assertThat(createdOrder.getProducts()).hasSize(1);
//        JpaOrderProduct selectedOrderProduct = createdOrder.getProducts().stream()
//                .filter(orderProduct -> orderProduct.getId().equals(warehouseProductDTO1.getId()))
//                .findAny()
//                .orElse(null);
//        assertThat(selectedOrderProduct).isNotNull();

        verify(eventSourcingService, times(1))
                .addOrderEvent(
                        argThat(event ->
                                event.order().id().equals(orderId)
                                && event.orderEventType().equals(OrderEvent.OrderEventType.CREATED)));
    }

}
