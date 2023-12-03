package com.codehunter.modulithproject.countdown_timer.business;

import com.codehunter.modulithproject.countdown_timer.*;
import com.codehunter.modulithproject.countdown_timer.domain.Event;
import com.codehunter.modulithproject.countdown_timer.mapper.EventMapper;
import com.codehunter.modulithproject.countdown_timer.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.codehunter.modulithproject.countdown_timer.mapper.EventMapper.toEvent;

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
        List<EventDTO> events = eventService.getAllByUser(UserMapper.toUser(user))
                .stream()
                .map(EventMapper::toEvent)
                .toList();
        return events;
    }


    @Override
    public EventDTO createEvent(@RequestBody CreateEventDTO createEventDTO, UserDTO user) {
        log.info("createEvent with userID: {}", user.id());
        Event event = Event.builder().name(createEventDTO.name())
                .date(createEventDTO.dateTime())
                .host(UserMapper.toUser(user))
                .build();
        Event newEvent = eventService.create(event);
        return toEvent(newEvent);
    }

    @Override
    public void deleteEvent(@PathVariable Long id) {
        log.info("deleteEvent with userID: {}", id);
        eventService.delete(id);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        log.info("getAllEvents by admin");
        return eventService.getAll().stream().map(EventMapper::toEvent).toList();
    }

    @Override
    public EventDTO updateEvent(Long id, UpdateEventDTO updateEventDTO, UserDTO user) {
        log.info("updateEvent with userID: {}", user.id());
        Event event = Event.builder().name(updateEventDTO.name())
                .id(new Event.EventId(id))
                .date(updateEventDTO.dateTime())
                .host(UserMapper.toUser(user))
                .build();
        Event updateEvent = eventService.update(event);
        return toEvent(updateEvent);
    }
}
