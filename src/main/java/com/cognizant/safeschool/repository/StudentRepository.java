package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student findStudentByEmail(String email);
}
