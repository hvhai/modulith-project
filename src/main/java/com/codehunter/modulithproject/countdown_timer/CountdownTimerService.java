package com.codehunter.modulithproject.countdown_timer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CountdownTimerService {
    CountdownTimerGreetingDTO sayHello(String name);

    List<EventDTO> getAllEventsByUser(UserDTO user);

    EventDTO createEvent(@RequestBody CreateEventDTO createEventDTO, UserDTO user);

    void deleteEvent(@PathVariable Long id);

    List<EventDTO> getAllEvents();

    EventDTO updateEvent(Long id, UpdateEventDTO updateEventDTO, UserDTO user);

    EventDTO getEventById(Long id, UserDTO user);
}
