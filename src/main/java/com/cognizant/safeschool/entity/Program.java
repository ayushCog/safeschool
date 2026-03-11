package com.cognizant.safeschool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Table(name = "programs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    @OneToMany(mappedBy = "program")
    private List<Training> trainings;
}