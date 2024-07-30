package com.codehunter.modulithproject.gateway.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ResponseFormatter {
    public static <T> ResponseEntity<ResponseDTO<Set<T>>> handleList(Set<T> items) {
        ResponseDTO<Set<T>> response = new ResponseDTO<>();
        response.setData(items);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseDTO<List<T>>> handleList(List<T> items) {
        ResponseDTO<List<T>> response = new ResponseDTO<>();
        response.setData(items);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    public static ResponseDTO<String> handleException(ErrorCodes errorCode, Optional<String> detailMsg) {
        ResponseDTO<String> response = new ResponseDTO<>();

        final List<ApplicationError> errors = new ArrayList<>();
        errors.add(ApplicationError.fromErrorCode(errorCode, detailMsg));
        response.setErrorList(errors);
        return response;
    }

    public static <T> ResponseEntity<ResponseDTO<T>> handleSingle(T item, HttpHeaders headers, HttpStatus status) {
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setData(item);
        return new ResponseEntity<>(response, headers, status);
    }
}
