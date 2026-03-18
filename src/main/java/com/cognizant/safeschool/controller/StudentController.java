package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.StudentDTO;
import com.cognizant.safeschool.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<StudentDTO> registerStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.registerStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) throws ResourceNotFoundException {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
}