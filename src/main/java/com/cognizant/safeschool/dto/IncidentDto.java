package com.cognizant.safeschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IncidentDto {
    private Long userId;
    private String type;
    private String location;
    private LocalDate date;
    private String status;
}
