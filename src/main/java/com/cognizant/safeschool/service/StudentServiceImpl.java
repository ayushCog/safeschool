package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.StudentDTO;
import com.cognizant.safeschool.entity.Student;
import com.cognizant.safeschool.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    // Constructor injection managed by Spring IoC via @RequiredArgsConstructor
    private final StudentRepo studentRepository;

    @Override
    public StudentDTO registerStudent(StudentDTO studentDTO) {
        // Mapping logic from DTO to Entity goes here
        Student student = new Student();
        // ... set fields ...
        Student savedStudent = studentRepository.save(student);
        // ... map back to DTO ...
        return studentDTO;
    }

    @Override
    public StudentDTO getStudentById(Long id) throws ResourceNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getStudentId());
        // map other fields
        return dto;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return List.of(); // Implement list mapping
    }
}