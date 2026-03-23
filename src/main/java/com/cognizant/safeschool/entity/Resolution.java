package com.cognizant.safeschool.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resolutionId;

    @OneToOne
    @JoinColumn(name = "incident_id", nullable = false)
    @NotNull(message = "Resolution must be linked to an Incident")
    private Incident incident;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Resolving officer is required")
    private User officer;

    @NotBlank(message = "Action details are required")
    private String actions;

    @NotNull(message = "Resolution date is required")
    private LocalDate date;

    @NotBlank(message = "Resolution status is required")
    private String status;
}