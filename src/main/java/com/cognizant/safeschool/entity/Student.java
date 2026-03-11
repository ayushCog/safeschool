package com.cognizant.safeschool.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
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
    private String contactInfo;
    private String status;

    @OneToMany(mappedBy = "student")
    private List<StudentDocument> documents;

    @OneToMany(mappedBy = "student")
    private List<Parent> parents;
}