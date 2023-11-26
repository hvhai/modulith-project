package com.codehunter.modulithproject.method_playground;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityDTO(String id, String type, List<String> capabilities) {
}
