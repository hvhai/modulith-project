package com.codehunter.modulithproject.countdown_timer;

import java.time.ZonedDateTime;

public record UpdateEventDTO(String name, ZonedDateTime dateTime) {
}
