package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.StudentException;
import com.cognizant.safeschool.dto.StudentResgistrationDto;
import com.cognizant.safeschool.entity.Student;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.StudentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.StudentRepository;
import com.cognizant.safeschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements IStudentService{
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public SuccessResponseProjection<StudentProjection> addStudent(StudentResgistrationDto studentResgistrationDto) throws StudentException {
        if(userRepository.findUserByEmail(studentResgistrationDto.getEmail()) != null){
            throw new StudentException("User already registered", HttpStatus.FOUND);
        }

        User user=new User();
        user.setName(studentResgistrationDto.getName());
        user.setEmail(studentResgistrationDto.getEmail());
        user.setPassword(studentResgistrationDto.getPassword());
        user.setRole(studentResgistrationDto.getRole());
        user.setPhone(studentResgistrationDto.getPhone());
        user.setStatus(studentResgistrationDto.getStatus());

        User savedUser=userRepository.save(user);

        Student student=new Student();
        student.setUser(savedUser);
        student.setDob(studentResgistrationDto.getDob());
        student.setGender(studentResgistrationDto.getGender());
        student.setAddress(studentResgistrationDto.getAddress());

        Student savedStudent=studentRepository.save(student);

        StudentProjection studentProjection=new StudentProjection(
                savedUser.getName(),
                savedUser.getRole(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getStatus(),
                savedStudent.getGender(),
                savedStudent.getAddress(),
                savedStudent.getDob()
        );

        return new SuccessResponseProjection<>(true, "Student registered successfully", studentProjection);
    }
}
