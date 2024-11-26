package com.codehunter.modulithproject.warehouse;

import com.codehunter.modulithproject.TestSecurityConfiguration;
import com.codehunter.modulithproject.shared.OrderDTO;
import com.codehunter.modulithproject.shared.OrderStatus;
import com.codehunter.modulithproject.shared.ProductDTO;
import com.codehunter.modulithproject.eventsourcing.WarehouseEvent;
import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import com.codehunter.modulithproject.warehouse.jpa_repository.WarehouseProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@ActiveProfiles("integration")
@Import(TestSecurityConfiguration.class)
class WarehouseDirectDependenciesIntegrationTest {
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseProductRepository warehouseProductRepository;

    @Test
    void reserveProductForOrder(Scenario scenario) {
        // given
        // Stock: Apple: 10
        List<JpaWarehouseProduct> allProduct = warehouseProductRepository.findAll();
        assertThat(allProduct).hasSize(3);
        JpaWarehouseProduct warehouseProductDTO1 = allProduct.get(0);
        assertThat(warehouseProductDTO1.getName()).isEqualTo("Apple");
        assertThat(warehouseProductDTO1.getQuantity()).isEqualTo(10);

        // when
        ProductDTO productDTO = new ProductDTO(
                warehouseProductDTO1.getId(),
                warehouseProductDTO1.getName(),
                warehouseProductDTO1.getPrice()
        );
        OrderDTO orderDTO = new OrderDTO(
                "1",
                OrderStatus.IN_PRODUCT_PREPARE,
                null,
                null,
                Set.of(productDTO)
        );

        // then
        scenario.stimulate(() -> warehouseService.reserveProductForOrder(orderDTO))
                .andWaitForEventOfType(WarehouseEvent.class)
                .matching(event -> event.orderId().equals(orderDTO.id())
                                   && event.warehouseEventType().equals(WarehouseEvent.WarehouseEventType.RESERVE_COMPLETED))
                .toArriveAndVerify(publishedEventAssert -> {
                    // Stock: Apple: 9
                    JpaWarehouseProduct updatedProduct = warehouseProductRepository.findById(warehouseProductDTO1.getId()).get();
                    assertThat(updatedProduct.getQuantity()).isEqualTo(9);
                });
    }
}
