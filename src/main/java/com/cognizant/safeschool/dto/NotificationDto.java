package com.cognizant.safeschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDto {
    private Long entityId;
    private String message;
    private String category;
    private String status;
    private LocalDateTime createdDate;
}
