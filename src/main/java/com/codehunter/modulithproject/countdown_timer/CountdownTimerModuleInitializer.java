package com.codehunter.modulithproject.countdown_timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.ApplicationModuleInitializer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CountdownTimerModuleInitializer implements ApplicationModuleInitializer {
    @Override
    public void initialize() {
        log.info("Init Countdown Timer module");
    }
}
