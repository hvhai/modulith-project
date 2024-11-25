package com.codehunter.modulithproject.countdown_timer.business;

import com.codehunter.modulithproject.countdown_timer.CountdownTimerGreetingDTO;
import com.codehunter.modulithproject.countdown_timer.CountdownTimerService;
import com.codehunter.modulithproject.countdown_timer.CreateEventDTO;
import com.codehunter.modulithproject.countdown_timer.EventDTO;
import com.codehunter.modulithproject.countdown_timer.UpdateEventDTO;
import com.codehunter.modulithproject.countdown_timer.UserDTO;
import com.codehunter.modulithproject.countdown_timer.domain.Event;
import com.codehunter.modulithproject.countdown_timer.mapper.EventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.codehunter.modulithproject.countdown_timer.mapper.EventMapper.toEventDTO;
import static com.codehunter.modulithproject.countdown_timer.mapper.UserMapper.toUser;

@Service
@Slf4j
public class CountdownTimerServiceImpl implements CountdownTimerService {
    private final EventServiceImpl eventService;

    public CountdownTimerServiceImpl(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @Override
    public CountdownTimerGreetingDTO sayHello(String name) {
        String phase = String.format("Hello %s from Countdown Timer Service", name);
        return new CountdownTimerGreetingDTO(phase);
    }

    @Override
    public List<EventDTO> getAllEventsByUser(UserDTO user) {
        log.info("getAllEventsByUser with userID: {}", user.id());
        List<EventDTO> events = eventService.getAllByUser(toUser(user))
                .stream()
                .map(EventMapper::toEventDTO)
                .toList();
        return events;
    }


    @Override
    public EventDTO createEvent(@RequestBody CreateEventDTO createEventDTO, UserDTO user) {
        log.info("createEvent with userID: {}", user.id());
        Event event = Event.builder().name(createEventDTO.name())
                .date(createEventDTO.dateTime())
                .host(toUser(user))
                .build();
        Event newEvent = eventService.create(event);
        return toEventDTO(newEvent);
    }

    @Override
    public void deleteEvent(@PathVariable Long id) {
        log.info("deleteEvent with userID: {}", id);
        eventService.delete(id);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        log.info("getAllEvents by admin");
        return eventService.getAll().stream().map(EventMapper::toEventDTO).toList();
    }

    @Override
    public EventDTO updateEvent(Long id, UpdateEventDTO updateEventDTO, UserDTO user) {
        log.info("updateEvent with userID: {}", user.id());
        Event event = Event.builder().name(updateEventDTO.name())
                .id(new Event.EventId(id))
                .date(updateEventDTO.dateTime())
                .host(toUser(user))
                .build();
        Event updateEvent = eventService.update(event);
        return toEventDTO(updateEvent);
    }

    @Override
    public EventDTO getEventById(Long id, UserDTO user) {
        Event event = eventService.findById(id, toUser(user));
        return toEventDTO(event);
    }
}
