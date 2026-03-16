package com.cognizant.safeschool.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complianceId;
    @Column(unique = true)
    private Long entityId;
    private String type;
    private String result;
    private LocalDate date;
    private String notes;
}