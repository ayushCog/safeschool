package com.cognizant.safeschool.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseProjection {
    private boolean isSuccess;
    private String message;
}
