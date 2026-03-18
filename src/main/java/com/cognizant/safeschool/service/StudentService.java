package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO registerStudent(StudentDTO studentDTO);
    StudentDTO getStudentById(Long id) throws ResourceNotFoundException;
    List<StudentDTO> getAllStudents();
}