package com.cognizant.safeschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParentResgistrationDto {
    private String name;
    private String role;
    private String email;
    private String phone;
    private String status;
    private String password;

    private String relation;
    private String studentEmail;
}
