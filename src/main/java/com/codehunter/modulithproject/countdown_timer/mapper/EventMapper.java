package com.codehunter.modulithproject.countdown_timer.mapper;

import com.codehunter.modulithproject.countdown_timer.EventDTO;
import com.codehunter.modulithproject.countdown_timer.domain.Event;

public class EventMapper {
    private EventMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static EventDTO toEvent(Event newEvent) {
        return new EventDTO(newEvent.getId().getValue(), newEvent.getName(), newEvent.getDate());
    }
}
