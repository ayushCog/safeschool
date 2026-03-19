package com.cognizant.safeschool.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponseProjection<T> {
    private boolean isSuccess;
    private String message;
    private T data;
}