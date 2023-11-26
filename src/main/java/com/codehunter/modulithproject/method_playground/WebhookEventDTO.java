package com.codehunter.modulithproject.method_playground;

import java.time.ZonedDateTime;

public record WebhookEventDTO(Long id, String data, ZonedDateTime createAt) {
}
