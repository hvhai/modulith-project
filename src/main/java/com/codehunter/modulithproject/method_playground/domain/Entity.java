package com.codehunter.modulithproject.method_playground.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class Entity {
    String id;
    String type;
    List<String> capabilities;
}
