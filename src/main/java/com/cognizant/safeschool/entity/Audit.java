package com.cognizant.safeschool.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Auditing officer is required")
    private User officer;

    @NotBlank(message = "Audit scope is required")
    private String scope;

    @NotBlank(message = "Audit findings are required")
    private String findings;

    @NotNull(message = "Audit date is required")
    @PastOrPresent(message = "Audit date cannot be in the future")
    private LocalDate date;

    @NotBlank(message = "Audit status is required")
    private String status;
}
