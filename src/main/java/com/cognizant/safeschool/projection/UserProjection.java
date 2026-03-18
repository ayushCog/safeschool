package com.cognizant.safeschool.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProjection {
    private String name;
    private String role;
    private String email;
    private String phone;
    private String status;
}
