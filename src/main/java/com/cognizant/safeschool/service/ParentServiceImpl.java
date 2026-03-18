package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.ParentResgistrationDto;
import com.cognizant.safeschool.entity.Parent;
import com.cognizant.safeschool.entity.Student;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.ParentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.ParentRepository;
import com.cognizant.safeschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParentServiceImpl implements IParentService {
    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SuccessResponseProjection<ParentProjection>  addParent(ParentResgistrationDto parentResgistrationDto) throws UserException {
        User studentUser=userRepository.findUserByEmail(parentResgistrationDto.getStudentEmail());

        if(studentUser==null || studentUser.getStudent()==null) {
            throw new UserException("Student not registered", HttpStatus.NOT_FOUND);
        }

        Student student=studentUser.getStudent();

        if(userRepository.findUserByEmail(parentResgistrationDto.getEmail()) != null) {
            throw new UserException("User already registered", HttpStatus.CONFLICT);
        }

        User user=new User();
        user.setName(parentResgistrationDto.getName());
        user.setEmail(parentResgistrationDto.getEmail());
        user.setPassword(parentResgistrationDto.getPassword());
        user.setRole(parentResgistrationDto.getRole());
        user.setPhone(parentResgistrationDto.getPhone());
        user.setStatus(parentResgistrationDto.getStatus());

        User savedUser=userRepository.save(user);

        Parent parent=new Parent();
        parent.setUser(savedUser);
        parent.setStudent(student);
        parent.setRelation(parentResgistrationDto.getRelation());

        Parent savedParent=parentRepository.save(parent);

        ParentProjection parentProjection=new ParentProjection(
                savedUser.getName(),
                savedUser.getRole(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getStatus(),
                savedParent.getRelation(),
                studentUser.getEmail()
        );

        return new SuccessResponseProjection<>(true, "Parent created successfully", parentProjection);
    }
}
