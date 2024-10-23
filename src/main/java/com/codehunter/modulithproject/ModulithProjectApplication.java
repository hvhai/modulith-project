package com.codehunter.modulithproject;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.List;

@EnableAsync
@SpringBootApplication
public class ModulithProjectApplication {
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(ModulithProjectApplication.class, args);
    }

    @PostConstruct
    public void printConfigProperties() {
        final List<String> propertyKeys = Arrays.asList(
                "management.endpoints.web.exposure.include",
                "management.endpoint.env.show-values",
                "management.tracing.sampling.probability",
                "management.tracing.enabled",
                "management.zipkin.tracing.endpoint",
                "spring.datasource.url",
                "spring.h2.console.enabled",
                "spring.h2.console.path",
                "spring.h2.console.settings.web-allow-others"
        );

        for (String key : propertyKeys) {
            System.out.println(key + " = '" + env.getProperty(key) + "'");
        }
    }
}
