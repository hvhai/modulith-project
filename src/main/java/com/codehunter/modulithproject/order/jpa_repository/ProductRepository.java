package com.codehunter.modulithproject.order.jpa_repository;

import com.codehunter.modulithproject.order.jpa.JpaProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<JpaProduct, String> {
}
