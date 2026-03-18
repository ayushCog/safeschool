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
public class TrainingDto {
    private Long programId;
    private Long userId;
    private LocalDate completionDate;
    private String status;
}
