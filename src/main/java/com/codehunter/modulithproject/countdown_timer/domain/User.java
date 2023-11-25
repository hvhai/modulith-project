package com.codehunter.modulithproject.countdown_timer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@AllArgsConstructor
@Data
@Builder
public class User {
    private String id;
    private String name;

    @Value
    public static class UserId {
        String value;
    }
}
