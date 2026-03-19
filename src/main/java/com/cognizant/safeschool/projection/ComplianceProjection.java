package com.cognizant.safeschool.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ComplianceProjection {
    private Long complianceId;
    private Long entityId;
    private String type;
    private String result;
    private LocalDate date;
    private String notes;
}