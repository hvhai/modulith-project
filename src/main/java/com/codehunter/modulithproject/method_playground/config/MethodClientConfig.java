package com.codehunter.modulithproject.method_playground.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MethodClientConfig {
    private final String authorizationHeaderName = "Authorization";
    @Value("${app.method.api.token}")
    private String authorizationHeaderValue;
    @Value("${app.method.url}")
    private String methodBaseUrl;

    @Bean
    public RestClient methodRestClient() {
        return RestClient.builder()
                .defaultHeader(authorizationHeaderName, "Bearer " + authorizationHeaderValue)
                .baseUrl(methodBaseUrl)
                .build();
    }
}
