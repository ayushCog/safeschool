package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Long> {
}
