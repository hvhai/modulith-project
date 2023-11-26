package com.codehunter.modulithproject.method_playground.method;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MethodEntity(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String id,
        MethodEntityType type,
        @JsonProperty("available_capabilities")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<MethodEntityCapabilityType> availableCapabilities) {
}
