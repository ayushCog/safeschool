package com.cognizant.safeschool.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String role;
    private String email;
    private String phone;
    private String status;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Parent parent;

    @OneToMany(mappedBy = "user")
    private List<AuditLog> auditLogs;

    @OneToMany(mappedBy = "staff")
    private List<Training> trainings;

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
    private List<Incident> reportedIncidents;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "officer", cascade = CascadeType.ALL)
    private List<Audit> audits;

    @OneToMany(mappedBy = "officer", cascade = CascadeType.ALL)
    private List<Resolution> resolutions;

    @OneToMany(mappedBy = "officer")
    private List<Drill> drills;
}
