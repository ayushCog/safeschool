package com.cognizant.safeschool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ResolutionDto {
    @NotNull(message = "Incident ID is required")
    private Long incidentId;

    @NotNull(message = "Resolving Officer ID is required")
    private Long userId;

    @NotBlank(message = "Action details are required")
    private String actions;

    @NotNull(message = "Resolution date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    private String status;
}
