package com.cognizant.safeschool.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ComplianceRecordRequestDTO {
    private Long entityId;
    private String type;
    private String result;
    private String notes;
    private Long officerId;
}