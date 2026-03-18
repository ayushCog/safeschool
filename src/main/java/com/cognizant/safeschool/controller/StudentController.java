package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.StudentResgistrationDto;
import com.cognizant.safeschool.projection.StudentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService iStudentService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponseProjection<StudentProjection>> registerStudent(@RequestBody StudentResgistrationDto studentResgistrationDto) {
        return ResponseEntity.status(HttpStatus.OK).body(iStudentService.addStudent(studentResgistrationDto));
    }
}
