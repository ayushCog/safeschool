package com.cognizant.safeschool.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private String scope;
    @Column(columnDefinition = "TEXT")
    private String metrics; // only for analysis
    private LocalDateTime generatedDate;
}