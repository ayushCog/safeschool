package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.UserRegistrationDto;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.UserProjection;
import com.cognizant.safeschool.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SuccessResponseProjection<UserProjection> addUser(UserRegistrationDto userRegistrationDto) throws UserException {
        log.info("Received service request: User registration attempt for Email: {} with Role: {}", userRegistrationDto.getEmail(), userRegistrationDto.getRole());

        if(userRepository.findUserByEmail(userRegistrationDto.getEmail()) != null){
            log.error("Registration failed: User with Email: {} already exists", userRegistrationDto.getEmail());
            throw new UserException("User already registered", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setRole(userRegistrationDto.getRole().toUpperCase());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPhone(userRegistrationDto.getPhone());
        user.setStatus(userRegistrationDto.getStatus());

        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        User savedUser = userRepository.save(user);

        log.info("Successfully registered User: {} with ID: {}", savedUser.getEmail(), savedUser.getUserId());

        return new SuccessResponseProjection<>(true, "User created successfully", new UserProjection(savedUser.getName(), savedUser.getRole(), savedUser.getEmail(), savedUser.getPhone(), savedUser.getStatus()));
    }
}
