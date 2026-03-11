package com.cognizant.safeschool.globalexception;

import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.projection.ErrorResponseProjection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserNotFound(UserException ex){
        ErrorResponseProjection errorResponse=new ErrorResponseProjection(false, ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
}
