package com.cognizant.safeschool.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "drills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drillId;

    @ManyToOne
    @JoinColumn(name = "emergency_id")
    @JsonIgnore
    private Emergency emergency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User officer;

    @Column(name = "drill_date")
    private LocalDate date;

    @Column(columnDefinition = "json")
    private String participantsJson;

    private String status;
}