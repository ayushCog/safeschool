package com.cognizant.safeschool.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incidentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reporter;

    private String type;
    private String location;
    private LocalDate date;
    private String status;

    @OneToOne(mappedBy = "incident", cascade = CascadeType.ALL)
    private Resolution resolution;
}