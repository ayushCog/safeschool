package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.StudentResgistrationDto;
import com.cognizant.safeschool.projection.StudentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

public interface IStudentService {
    public SuccessResponseProjection<StudentProjection> addStudent(StudentResgistrationDto studentResgistrationDto);
}
