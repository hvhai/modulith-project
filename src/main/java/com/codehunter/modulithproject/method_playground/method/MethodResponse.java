package com.codehunter.modulithproject.method_playground.method;

public record MethodResponse<T>(String success, T data, String message) {
}
