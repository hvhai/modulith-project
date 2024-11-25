package com.codehunter.modulithproject.gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        final String apiTitle = String.format("%s API", StringUtils.capitalize("Modulith Project"));
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)) // this line make all method must be authenticated
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info().title(apiTitle).version("v1"));
    }

    @Bean
    public GroupedOpenApi countdownTimerApi() {
        return GroupedOpenApi.builder()
                .group("countdown-timer")
                .pathsToMatch("/api/countdown-timer/**")
                .build();
    }

    @Bean
    public GroupedOpenApi methodPlaygroundApi() {
        return GroupedOpenApi.builder()
                .group("method-playground")
                .pathsToMatch("/api/method-playground/**")
                .build();
    }

    @Bean
    public GroupedOpenApi fruitOrderingApi() {
        return GroupedOpenApi.builder()
                .group("fruit-ordering")
                .pathsToMatch("/api/fruit-ordering/**")
                .build();
    }
}
