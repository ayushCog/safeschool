package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.UserRegistrationDto;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.UserProjection;
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
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void addUser_ValidTest() throws UserException {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setName("Test");
        dto.setEmail("test@example.com");
        dto.setRole("OFFICER");
        dto.setPassword("securePass");

        when(userRepository.findUserByEmail(dto.getEmail())).thenReturn(null);

        User savedUser = new User();
        savedUser.setName(dto.getName());
        savedUser.setRole(dto.getRole());
        savedUser.setEmail(dto.getEmail());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        SuccessResponseProjection<UserProjection> response = userService.addUser(dto);

        assertTrue(response.isSuccess());
        assertEquals("User created successfully", response.getMessage());
        assertEquals("Test", response.getData().getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void addUser_DuplicateEmail_ThrowsException() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("existing@example.com");

        User existingUser = new User();
        existingUser.setEmail("existing@example.com");
        when(userRepository.findUserByEmail(dto.getEmail())).thenReturn(existingUser);

        UserException exception = assertThrows(UserException.class, () -> {
            userService.addUser(dto);
        });

        assertEquals("User already registered", exception.getMessage());
        assertEquals(HttpStatus.FOUND, exception.getHttpStatus());
        verify(userRepository, never()).save(any(User.class));
    }
}
