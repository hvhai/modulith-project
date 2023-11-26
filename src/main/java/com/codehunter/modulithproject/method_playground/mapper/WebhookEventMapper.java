package com.codehunter.modulithproject.method_playground.mapper;

import com.codehunter.modulithproject.method_playground.WebhookEventDTO;
import com.codehunter.modulithproject.method_playground.jpa.JpaWebhookEvent;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public final class WebhookEventMapper {
    public static WebhookEventDTO toWebhookEventDTO(JpaWebhookEvent jpaWebhookEvent) {
        return new WebhookEventDTO(jpaWebhookEvent.getId(), jpaWebhookEvent.getData(), ZonedDateTime.ofInstant(jpaWebhookEvent.getCreateAt(), ZoneOffset.UTC));
    }
}
