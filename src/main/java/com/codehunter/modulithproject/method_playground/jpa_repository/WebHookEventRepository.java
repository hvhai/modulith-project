package com.codehunter.modulithproject.method_playground.jpa_repository;

import com.codehunter.modulithproject.method_playground.jpa.JpaWebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebHookEventRepository extends JpaRepository<JpaWebhookEvent, Long> {
    List<JpaWebhookEvent> findAllByOrderByIdDesc();
}
