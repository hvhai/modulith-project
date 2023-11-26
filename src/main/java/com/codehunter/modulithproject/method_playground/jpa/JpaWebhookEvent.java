package com.codehunter.modulithproject.method_playground.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "method_webhook_event")
@Setter
@Getter
public class JpaWebhookEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String data;
    private Instant createAt;

}
