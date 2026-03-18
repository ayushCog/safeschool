package com.cognizant.safeschool.dto;

import lombok.Data;

@Data
public class ParentDTO {
    private Long parentId;
    private Long userId;
    private Long studentId;
    private String relation;
    private String status;
}
