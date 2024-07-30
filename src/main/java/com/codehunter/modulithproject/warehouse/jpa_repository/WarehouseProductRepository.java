package com.codehunter.modulithproject.warehouse.jpa_repository;

import com.codehunter.modulithproject.warehouse.jpa.JpaWarehouseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseProductRepository extends JpaRepository<JpaWarehouseProduct, String> {
}
