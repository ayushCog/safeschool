package com.cognizant.safeschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ResolutionDto {
    private Long incidentId;
    private Long userId;
    private String actions;
    private LocalDate date;
    private String status;
}
