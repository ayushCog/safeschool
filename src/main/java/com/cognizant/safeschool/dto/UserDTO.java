package com.cognizant.safeschool.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String role;
    private String email;
    private String status;
}