package com.codehunter.modulithproject.method_playground;

import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.ApplicationModuleInitializer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MethodPlaygroundModuleInitializer implements ApplicationModuleInitializer {
    @Override
    public void initialize() {
        log.info("Init Method Playground module");
    }
}
