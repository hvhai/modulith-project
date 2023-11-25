package com.codehunter.modulithproject.gateway;

import com.codehunter.modulithproject.countdown_timer.CountdownTimerGreetingDTO;
import com.codehunter.modulithproject.countdown_timer.CountdownTimerService;
import com.codehunter.modulithproject.countdown_timer.CreateEventDTO;
import com.codehunter.modulithproject.countdown_timer.EventDTO;
import com.codehunter.modulithproject.gateway.mapper.UserMapper;
import com.codehunter.modulithproject.gateway.response.ResponseDTO;
import com.codehunter.modulithproject.gateway.response.ResponseFormatter;
import com.codehunter.modulithproject.gateway.util.AuthenticationUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countdown-timer")
public class CountdownTimerController {
    private final CountdownTimerService countdownTimerService;

    public CountdownTimerController(CountdownTimerService countdownTimerService) {
        this.countdownTimerService = countdownTimerService;
    }

    @GetMapping("/greeting")
    public ResponseEntity<ResponseDTO<CountdownTimerGreetingDTO>> countdownTimerSayGreeting() {
        CountdownTimerGreetingDTO countdownTimerGreetingDTO = countdownTimerService.sayHello("Spring Modulith");
        return ResponseFormatter.handleSingle(countdownTimerGreetingDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<ResponseDTO<List<EventDTO>>> getAllEventsByUser() {
        UserDTO user = AuthenticationUtil.getUser();
        List<EventDTO> events = countdownTimerService.getAllEventsByUser(UserMapper.toCoundownTimerUserDTO(user));
        return ResponseFormatter.handleList(events);
    }


    @PostMapping("/events")
    public ResponseEntity<ResponseDTO<EventDTO>> createNewEvent(@RequestBody CreateEventDTO createEventDto) {
        UserDTO user = AuthenticationUtil.getUser();
        EventDTO newEvent = countdownTimerService.createEvent(createEventDto, UserMapper.toCoundownTimerUserDTO(user));
        return ResponseFormatter.handleSingle(newEvent, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/events/{id}")
    public void deleteEvent(@PathVariable Long id) {
        countdownTimerService.deleteEvent(id);
    }

    @GetMapping(path = "/admin/events")
    public ResponseEntity<ResponseDTO<List<EventDTO>>> getAllEvents() {
        List<EventDTO> events = countdownTimerService.getAllEvents();
        return ResponseFormatter.handleList(events);
    }
}
