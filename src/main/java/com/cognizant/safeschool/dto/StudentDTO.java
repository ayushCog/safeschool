package com.cognizant.safeschool.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO {
    private Long studentId;
    private Long userId;
    private LocalDate dob;
    private String gender;
    private String address;
    private String contactInfo;
    private String status;
}