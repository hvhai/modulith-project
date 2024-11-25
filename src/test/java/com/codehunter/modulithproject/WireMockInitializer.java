package com.codehunter.modulithproject;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Map;

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        WireMockServer oauthWireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
        oauthWireMockServer.start();
        configurableApplicationContext
                .getBeanFactory()
                .registerSingleton("oauthWireMockServer", oauthWireMockServer);

        configurableApplicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                oauthWireMockServer.stop();
            }
        });

        TestPropertyValues
                .of(Map.of("app.wiremock.oauth2.url", "http://localhost:" + oauthWireMockServer.port() + "/wiremock-auth"))
                .applyTo(configurableApplicationContext);
    }
}
