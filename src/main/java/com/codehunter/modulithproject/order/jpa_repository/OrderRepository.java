package com.codehunter.modulithproject.order.jpa_repository;

import com.codehunter.modulithproject.order.jpa.JpaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<JpaOrder, String> {
}
