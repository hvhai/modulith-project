package com.codehunter.modulithproject.method_playground.business;

import com.codehunter.modulithproject.method_playground.MethodPlaygroundGreetingDTO;
import com.codehunter.modulithproject.method_playground.MethodPlaygroundService;
import org.springframework.stereotype.Service;

@Service
public class MethodPlaygroundServiceImpl implements MethodPlaygroundService {
    @Override
    public MethodPlaygroundGreetingDTO sayHello(String name) {
        String phase = String.format("Hello %s from Method Playground Service", name);
        return new MethodPlaygroundGreetingDTO(phase);
    }
}
