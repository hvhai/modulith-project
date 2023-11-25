package com.codehunter.modulithproject.countdown_timer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
@Builder
public class Event {
    private EventId id;
    private String name;
    private ZonedDateTime date;
    private User host;

    @Value
    public static class EventId {
        Long value;
    }

}

