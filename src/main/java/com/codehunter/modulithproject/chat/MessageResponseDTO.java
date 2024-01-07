package com.codehunter.modulithproject.chat;

import java.time.Instant;

public record MessageResponseDTO(String from, String text, Instant time) {
}
