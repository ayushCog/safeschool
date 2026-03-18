package com.cognizant.safeschool.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDocumentDTO {
    private Long documentId;
    private Long studentId;
    private String docType;
    private String fileUri;
    private LocalDate uploadedDate;
    private String verificationStatus;
}