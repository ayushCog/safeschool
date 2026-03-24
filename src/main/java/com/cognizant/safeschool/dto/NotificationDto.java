package com.cognizant.safeschool.dto;

import lombok.*;

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
