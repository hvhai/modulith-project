package com.codehunter.modulithproject.gateway;

import com.codehunter.modulithproject.gateway.response.ResponseDTO;
import com.codehunter.modulithproject.gateway.response.ResponseFormatter;
import com.codehunter.modulithproject.method_playground.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/method-playground")
@Slf4j
public class MethodPlaygroundController {
    private final MethodPlaygroundService methodPlaygroundService;

    public MethodPlaygroundController(MethodPlaygroundService methodPlaygroundService) {
        this.methodPlaygroundService = methodPlaygroundService;
    }

    @GetMapping("/greeting")
    public ResponseEntity<ResponseDTO<MethodPlaygroundGreetingDTO>> methodPlaygroundSayGreeting() {
        MethodPlaygroundGreetingDTO methodPlaygroundGreetingDTO = methodPlaygroundService.sayHello("Spring Modulith");
        return ResponseFormatter.handleSingle(methodPlaygroundGreetingDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/entities")
    public ResponseEntity<ResponseDTO<List<EntityDTO>>> methodPlaygroundGetAllEntities() {
        List<EntityDTO> allEntities = methodPlaygroundService.getAllEntities();
        return ResponseFormatter.handleList(allEntities);
    }

    @PostMapping("/webhooks")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO<WebhookEventDTO>> createWebhook(@RequestBody WebhookEventRequest event) {
        log.info("Receive webhook  event : {}", event);
        WebhookEventDTO newWebhookEvent = methodPlaygroundService.createWebhookEvent(event);
        return ResponseFormatter.handleSingle(newWebhookEvent, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/webhooks")
    public ResponseEntity<ResponseDTO<List<WebhookEventDTO>>> getAllWebhookEvents() {
        List<WebhookEventDTO> allWebhookEvents = methodPlaygroundService.getAllWebhookEvents();
        return ResponseFormatter.handleList(allWebhookEvents);
    }
}
