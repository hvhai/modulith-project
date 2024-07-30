package com.codehunter.modulithproject.payment.jpa_repository;

import com.codehunter.modulithproject.payment.jpa.JpaPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<JpaPayment, String> {
}
