package com.codehunter.modulithproject.order.jpa_repository;

import com.codehunter.modulithproject.order.jpa.JpaOrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPaymentRepository extends JpaRepository<JpaOrderPayment, String> {
}
