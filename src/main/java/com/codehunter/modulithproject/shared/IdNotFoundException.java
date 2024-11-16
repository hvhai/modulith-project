package com.codehunter.modulithproject.shared;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String id) {
        super(String.format("The specified id %s was not found", id));
    }
}
