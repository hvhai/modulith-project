package com.codehunter.modulithproject.order.jpa_repository;

import com.codehunter.modulithproject.order.jpa.JpaOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<JpaOrderProduct, String> {
}
