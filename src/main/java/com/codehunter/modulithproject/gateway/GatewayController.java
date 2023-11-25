package com.codehunter.modulithproject.gateway;

import com.codehunter.modulithproject.countdown_timer.CountdownTimerGreetingDTO;
import com.codehunter.modulithproject.countdown_timer.CountdownTimerService;
import com.codehunter.modulithproject.gateway.response.ResponseDTO;
import com.codehunter.modulithproject.gateway.response.ResponseFormatter;
import com.codehunter.modulithproject.method_playground.MethodPlaygroundGreetingDTO;
import com.codehunter.modulithproject.method_playground.MethodPlaygroundService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GatewayController {
    private final CountdownTimerService countdownTimerService;
    private final MethodPlaygroundService methodPlaygroundService;

    public GatewayController(CountdownTimerService countdownTimerService, MethodPlaygroundService methodPlaygroundService) {
        this.countdownTimerService = countdownTimerService;
        this.methodPlaygroundService = methodPlaygroundService;
    }

    @GetMapping("/countdown-timer/greeting")
    public ResponseEntity<ResponseDTO<CountdownTimerGreetingDTO>> countdownTimerSayGreeting() {
        CountdownTimerGreetingDTO countdownTimerGreetingDTO = countdownTimerService.sayHello("Spring Modulith");
        return ResponseFormatter.handleSingle(countdownTimerGreetingDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/method-playground/greeting")
    public ResponseEntity<ResponseDTO<MethodPlaygroundGreetingDTO>> methodPlaygroundSayGreeting() {
        MethodPlaygroundGreetingDTO methodPlaygroundGreetingDTO = methodPlaygroundService.sayHello("Spring Modulith");
        return ResponseFormatter.handleSingle(methodPlaygroundGreetingDTO, new HttpHeaders(), HttpStatus.OK);
    }
}
