package com.cognizant.safeschool.classexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmergencyException extends RuntimeException {

    private final HttpStatus httpStatus;

    public EmergencyException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}