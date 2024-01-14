package com.codehunter.modulithproject.order.init;

import com.codehunter.modulithproject.order.jpa.JpaProduct;
import com.codehunter.modulithproject.order.jpa_repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class InitData implements ApplicationListener<ContextRefreshedEvent> {
    private final ProductRepository productRepository;

    public InitData(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("onApplicationEvent: App STARTED");
        JpaProduct product1 = new JpaProduct();
        product1.setName("Apple");
        JpaProduct product2 = new JpaProduct();
        product2.setName("Orange");
        JpaProduct product3 = new JpaProduct();
        product3.setName("Lemon");
        List<JpaProduct> jpaProducts = productRepository.saveAll(List.of(product1, product2, product3));
        jpaProducts.stream().forEach(product -> log.info(product.toString()));
    }
}
