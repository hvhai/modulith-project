package com.codehunter.modulithproject.countdown_timer;

import java.time.ZonedDateTime;

public record EventDTO(Long id, String name, ZonedDateTime dateTime) {
}
