package com.cognizant.safeschool.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate dob;
    private String gender;
    private String address;

    @OneToMany(mappedBy = "student")
    private List<StudentDocument> documents;

    @ManyToMany
    @JoinTable(
        name = "student_drills",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "drill_id")
    )
    private List<Drill> drills;

    @OneToMany(mappedBy = "student")
    private List<Parent> parents;
}