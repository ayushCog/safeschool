package com.cognizant.safeschool.classexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends Exception {
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public ValidationException(String message) {
        super(message);
    }
}