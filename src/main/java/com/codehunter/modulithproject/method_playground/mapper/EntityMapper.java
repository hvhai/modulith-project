package com.codehunter.modulithproject.method_playground.mapper;

import com.codehunter.modulithproject.method_playground.EntityDTO;
import com.codehunter.modulithproject.method_playground.domain.Entity;
import com.codehunter.modulithproject.method_playground.method.MethodEntity;
import com.codehunter.modulithproject.method_playground.method.MethodEntityCapabilityType;

public final class EntityMapper {
    public static EntityDTO toEntityDTO(MethodEntity method) {
        return new EntityDTO(
                method.id(),
                method.type().getText(),
                method.availableCapabilities()
                        .stream()
                        .map(MethodEntityCapabilityType::getText)
                        .toList());
    }

    public static Entity toEntity(MethodEntity method) {
        return new Entity(
                method.id(),
                method.type().getText(),
                method.availableCapabilities()
                        .stream()
                        .map(MethodEntityCapabilityType::getText)
                        .toList());
    }

    public static EntityDTO toEntityDTO(Entity method) {
        return new EntityDTO(
                method.getId(),
                method.getType(),
                method.getCapabilities());
    }
}
