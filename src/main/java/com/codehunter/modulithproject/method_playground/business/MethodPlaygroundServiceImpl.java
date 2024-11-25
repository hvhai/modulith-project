package com.codehunter.modulithproject.method_playground.business;

import com.codehunter.modulithproject.method_playground.EntityDTO;
import com.codehunter.modulithproject.method_playground.MethodPlaygroundGreetingDTO;
import com.codehunter.modulithproject.method_playground.MethodPlaygroundService;
import com.codehunter.modulithproject.method_playground.WebhookEventDTO;
import com.codehunter.modulithproject.method_playground.WebhookEventRequest;
import com.codehunter.modulithproject.method_playground.jpa.JpaWebhookEvent;
import com.codehunter.modulithproject.method_playground.jpa_repository.WebHookEventRepository;
import com.codehunter.modulithproject.method_playground.mapper.EntityMapper;
import com.codehunter.modulithproject.method_playground.mapper.WebhookEventMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MethodPlaygroundServiceImpl implements MethodPlaygroundService {
    private final MethodServiceImpl methodService;
    private final WebHookEventRepository webHookEventRepository;

    public MethodPlaygroundServiceImpl(MethodServiceImpl methodService, WebHookEventRepository webHookEventRepository) {
        this.methodService = methodService;
        this.webHookEventRepository = webHookEventRepository;
    }

    @Override
    public MethodPlaygroundGreetingDTO sayHello(String name) {
        String phase = String.format("Hello %s from Method Playground Service", name);
        return new MethodPlaygroundGreetingDTO(phase);
    }

    @Override
    public List<EntityDTO> getAllEntities() {
        return methodService.getAllEntities().stream().map(EntityMapper::toEntityDTO).toList();
    }

    @Override
    public WebhookEventDTO createWebhookEvent(WebhookEventRequest webhookEventRequest) {
        JpaWebhookEvent event = new JpaWebhookEvent();
        event.setData(webhookEventRequest.toString());
        event.setCreateAt(Instant.now());
        JpaWebhookEvent newEvent = webHookEventRepository.save(event);
        return WebhookEventMapper.toWebhookEventDTO(newEvent);
    }

    @Override
    public List<WebhookEventDTO> getAllWebhookEvents() {
        List<JpaWebhookEvent> allByOrderByIdDesc = webHookEventRepository.findAllByOrderByIdDesc();
        return allByOrderByIdDesc.stream().map(WebhookEventMapper::toWebhookEventDTO).toList();
    }
}
