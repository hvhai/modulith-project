package com.codehunter.modulithproject.countdown_timer.business;

import com.codehunter.modulithproject.countdown_timer.CountdownTimerService;
import com.codehunter.modulithproject.countdown_timer.CountdownTimerGreetingDTO;
import org.springframework.stereotype.Service;

@Service
public class CountdownTimerServiceImpl implements CountdownTimerService {
    @Override
    public CountdownTimerGreetingDTO sayHello(String name) {
        String phase = String.format("Hello %s from Countdown Timer Service", name);
        return new CountdownTimerGreetingDTO(phase);
    }
}
