package com.cognizant.safeschool.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponseProjection<T> {
    boolean isSuccess;
    String message;
    T data;
}
