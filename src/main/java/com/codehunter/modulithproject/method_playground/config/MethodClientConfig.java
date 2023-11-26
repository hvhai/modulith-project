package com.codehunter.modulithproject.method_playground.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class MethodClientConfig {
    private final String authorizationHeaderName = "Authorization";
    @Value("${app.method.api.token}")
    private String authorizationHeaderValue;

    @Bean
    public RestTemplate methodClient() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new CustomHeaderInterceptor(authorizationHeaderName, "Bearer " + authorizationHeaderValue)));
        return restTemplate;
    }
}
