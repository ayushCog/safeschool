package com.cognizant.safeschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drillId;

    @ManyToOne
    @JoinColumn(name = "emergency_id")
    private Emergency emergency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User officer;

    @Column(name = "drill_date")
    private LocalDate date;

    @Column(columnDefinition = "json")
    private String participantsJson;

    @ManyToMany(mappedBy = "drills")
    private List<Student> students;

    private String status;
}