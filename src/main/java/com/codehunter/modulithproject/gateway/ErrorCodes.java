package com.codehunter.modulithproject.gateway;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodes {
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found", "Requested item was not found."),
    BAD_CLIENT_REQUEST(HttpStatus.BAD_REQUEST, "Invalid client request", "Received an invalid client request"),
    GENERIC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Generic Exception", "An unexpected error was encountered."),
    UNHANDLED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unhandled Exception", "The service failed to handle an error.");

    private final HttpStatus status;
    private final String title;
    private final String detail;

    ErrorCodes(HttpStatus status, String title, String detail) {
        this.status = status;
        this.title = title;
        this.detail = detail;
    }
}
