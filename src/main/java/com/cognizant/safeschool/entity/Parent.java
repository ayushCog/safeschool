package com.cognizant.safeschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parents")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String relation;
    private String status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}