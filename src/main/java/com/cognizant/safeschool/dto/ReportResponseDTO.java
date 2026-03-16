package com.cognizant.safeschool.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReportResponseDTO {
    private Long reportId;
    private String scope;
    private String metrics;
    private LocalDateTime generatedDate;
}