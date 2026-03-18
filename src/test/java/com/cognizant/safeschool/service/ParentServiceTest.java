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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParentServiceTest {
    @Mock
    private ParentRepository parentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ParentServiceImpl parentService;

    @Test
    public void addParent_ValidTest() throws UserException {
        ParentResgistrationDto dto = new ParentResgistrationDto();
        dto.setStudentEmail("student@school.com");
        dto.setEmail("parent@home.com");
        dto.setName("Parent Name");
        dto.setRelation("FATHER");

        User studentUser = new User();
        studentUser.setEmail("student@school.com");
        Student student = new Student();
        studentUser.setStudent(student);

        when(userRepository.findUserByEmail("student@school.com")).thenReturn(studentUser);
        when(userRepository.findUserByEmail("parent@home.com")).thenReturn(null);

        User savedUser = new User();
        savedUser.setName(dto.getName());
        savedUser.setEmail(dto.getEmail());

        Parent savedParent = new Parent();
        savedParent.setUser(savedUser);
        savedParent.setRelation("FATHER");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(parentRepository.save(any(Parent.class))).thenReturn(savedParent);

        SuccessResponseProjection<ParentProjection> response = parentService.addParent(dto);

        assertTrue(response.isSuccess());
        assertEquals("Parent created successfully", response.getMessage());
        assertEquals("student@school.com", response.getData().getStudentEmail());
        verify(parentRepository, times(1)).save(any(Parent.class));
    }

    @Test
    public void addParent_StudentNotFound_ThrowsException() {
        ParentResgistrationDto dto = new ParentResgistrationDto();
        dto.setStudentEmail("nonexistent@school.com");

        when(userRepository.findUserByEmail(dto.getStudentEmail())).thenReturn(null);

        UserException exception = assertThrows(UserException.class, () -> {
            parentService.addParent(dto);
        });

        assertEquals("Student not registered", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(parentRepository, never()).save(any());
    }

    @Test
    public void addParent_DuplicateParentEmail_ThrowsException() {
        ParentResgistrationDto dto = new ParentResgistrationDto();
        dto.setStudentEmail("student@school.com");
        dto.setEmail("alreadyexists@home.com");

        User studentUser = new User();
        studentUser.setStudent(new Student());

        when(userRepository.findUserByEmail(dto.getStudentEmail())).thenReturn(studentUser);
        when(userRepository.findUserByEmail(dto.getEmail())).thenReturn(new User());

        UserException exception = assertThrows(UserException.class, () -> {
            parentService.addParent(dto);
        });

        assertEquals("User already registered", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
    }
}
