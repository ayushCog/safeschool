package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.StudentDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDocumentRepo extends JpaRepository<StudentDocument, Long> {
}
