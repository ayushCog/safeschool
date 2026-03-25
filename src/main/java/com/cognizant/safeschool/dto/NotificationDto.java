package com.cognizant.safeschool.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDto {
    private Long entityId=1L;

    @NotBlank(message = "Message is required")
    private String message;

    private String category="Not applicable";
    private String status;
    private LocalDateTime createdDate;
}
