package com.codehunter.modulithproject.gateway;

import com.codehunter.modulithproject.countdown_timer.*;
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

    @PutMapping(path = "/events/{id}")
    public ResponseEntity<ResponseDTO<EventDTO>> updateEvent(@PathVariable Long id, @RequestBody UpdateEventDTO updateEventDTO) {
        UserDTO user = AuthenticationUtil.getUser();
        EventDTO updatedEvent = countdownTimerService.updateEvent(id, updateEventDTO, UserMapper.toCoundownTimerUserDTO(user));
        return ResponseFormatter.handleSingle(updatedEvent, new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping(path = "/events/{id}")
    public ResponseEntity<ResponseDTO<EventDTO>> getEventById(@PathVariable Long id) {
        UserDTO user = AuthenticationUtil.getUser();
        EventDTO updatedEvent = countdownTimerService.getEventById(id, UserMapper.toCoundownTimerUserDTO(user));
        return ResponseFormatter.handleSingle(updatedEvent, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/admin/events")
    public ResponseEntity<ResponseDTO<List<EventDTO>>> getAllEvents() {
        List<EventDTO> events = countdownTimerService.getAllEvents();
        return ResponseFormatter.handleList(events);
    }
}
