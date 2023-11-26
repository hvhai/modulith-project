package com.codehunter.modulithproject.method_playground.method;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @see <a href="https://docs.methodfi.com/api/core/entities/object#capabilities">Method | The capabilities of the entity.</a>
 */
public enum MethodEntityCapabilityType {
    PAYMENTS_RECEIVE("payments:receive"),
    PAYMENTS_SEND("payments:send"),
    DATA_RETRIEVE("data:retrieve"),
    DATA_SYNC("data:sync");

    final String text;

    MethodEntityCapabilityType(String text) {
        this.text = text;
    }

    @JsonValue
    public String getText() {
        return text;
    }
}
