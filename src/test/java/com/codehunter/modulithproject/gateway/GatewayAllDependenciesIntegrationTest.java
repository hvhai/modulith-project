package com.codehunter.modulithproject.gateway;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.codehunter.modulithproject.TestSecurityConfiguration;
import com.codehunter.modulithproject.order.jpa.JpaOrder;
import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import com.codehunter.modulithproject.order.jpa_repository.OrderRepository;
import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import com.codehunter.modulithproject.payment.jpa_repository.PaymentRepository;
import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.shared.PaymentEvent;
import com.codehunter.modulithproject.shared.ProductDTO;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
@ActiveProfiles("integration")
@Import(TestSecurityConfiguration.class)
@Sql(scripts = "classpath:datasource/schema-integration.sql")
@TestPropertySource(properties = {
        "spring.main.lazy-initialization=true",
        "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true"
})
class GatewayAllDependenciesIntegrationTest {
    @Autowired
    private FruitOrderingController fruitOrderingController;

    @Autowired
    private WarehouseProductRepository warehouseProductRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;

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

        List<JpaOrder> allOrder = orderRepository.findAll();
        assertThat(allOrder).hasSize(0);


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

//        fruitOrderingController.createOrder(orderDTO);

        // when
        scenario.stimulate(() -> fruitOrderingController.createOrder(orderDTO))
                .andWaitForEventOfType(PaymentEvent.class)
                .matching(event -> event.paymentEventType().equals(PaymentEvent.PaymentEventType.CREATED))
                .toArriveAndVerify(publishedEventAssert -> {
                    // then
                    // Order created
                    List<JpaOrder> allOrderAfterCreate = orderRepository.findAll();
                    assertThat(allOrderAfterCreate).hasSize(1);
                    String orderId = allOrderAfterCreate.get(0).getId();
                    JpaOrder createdOrder = orderRepository.findById(orderId).get();

                    // with selected product
                    assertThat(createdOrder.getProducts()).hasSize(1);
                    JpaOrderProduct selectedOrderProduct = createdOrder.getProducts().stream()
                            .filter(orderProduct -> orderProduct.getId().equals(warehouseProductDTO1.getId()))
                            .findAny()
                            .orElse(null);
                    assertThat(selectedOrderProduct).isNotNull();

                    // Stock quantity reduce 1: Apple: 9
                    JpaWarehouseProduct updatedProduct = warehouseProductRepository.findById(warehouseProductDTO1.getId()).get();
                    assertThat(updatedProduct.getQuantity()).isEqualTo(9);

                    // Payment created with null purchaseAt
                    List<JpaPayment> payments = paymentRepository.findByOrderId(orderId);
                    assertThat(payments).hasSize(1);
                    assertThat(payments.getFirst().getPurchaseAt()).isNull();
                });
    }
}
