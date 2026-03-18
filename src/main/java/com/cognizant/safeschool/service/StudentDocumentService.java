package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.StudentDocumentDTO;

import java.util.List;

public interface StudentDocumentService {
    StudentDocumentDTO uploadDocument(StudentDocumentDTO documentDTO);
    StudentDocumentDTO getDocumentById(Long id) throws ResourceNotFoundException;
    List<StudentDocumentDTO> getDocumentsByStudentId(Long studentId);
}