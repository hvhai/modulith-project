package com.codehunter.modulithproject.method_playground.method;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @see <a href="https://docs.methodfi.com/api/core/entities/object/#type">Method | The type of the entity.</a>
 */
public enum MethodEntityType {
    INDIVIDUAL("individual"),
    C_CORPORATION("c_corporation");

    final String text;

    MethodEntityType(String text) {
        this.text = text;
    }

    @JsonValue
    public String getText() {
        return text;
    }
}
