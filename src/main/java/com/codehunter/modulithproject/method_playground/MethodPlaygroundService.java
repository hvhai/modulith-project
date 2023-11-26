package com.codehunter.modulithproject.method_playground;

import java.util.List;

public interface MethodPlaygroundService {
    MethodPlaygroundGreetingDTO sayHello(String name);

    List<EntityDTO> getAllEntities();

    WebhookEventDTO createWebhookEvent(WebhookEventRequest webhookEventRequest);
    List<WebhookEventDTO> getAllWebhookEvents();
}
