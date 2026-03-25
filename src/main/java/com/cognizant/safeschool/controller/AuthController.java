package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.classexception.SecurityException;
import com.cognizant.safeschool.dto.LoginRequestDto;
import com.cognizant.safeschool.dto.ParentRegistrationDto;
import com.cognizant.safeschool.dto.StudentRegistrationDto;
import com.cognizant.safeschool.dto.UserRegistrationDto;
import com.cognizant.safeschool.projection.*;
import com.cognizant.safeschool.service.IParentService;
import com.cognizant.safeschool.service.IStudentService;
import com.cognizant.safeschool.service.IUserService;
import com.cognizant.safeschool.service.UserDetailsServiceImpl;
import com.cognizant.safeschool.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IParentService parentService;

    @PostMapping("/register/user")
    public ResponseEntity<SuccessResponseProjection<UserProjection>> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        log.info("Received POST request: Registration attempt for User: {} with Role: {}", userRegistrationDto.getEmail(), userRegistrationDto.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(userRegistrationDto));
    }

    @PostMapping("/register/student")
    public ResponseEntity<SuccessResponseProjection<StudentProjection>> registerStudent(@Valid @RequestBody StudentRegistrationDto studentRegistrationDto) {
        log.info("Received POST request: Student registration attempt for Email: {}", studentRegistrationDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(studentService.addStudent(studentRegistrationDto));
    }

    @PostMapping("/register/parent")
    public ResponseEntity<SuccessResponseProjection<ParentProjection>> registerParent(@Valid @RequestBody ParentRegistrationDto parentRegistrationDto) {
        log.info("Received POST request: Parent registration attempt for Email: {} linked to Student: {}", parentRegistrationDto.getEmail(), parentRegistrationDto.getStudentEmail());
        return ResponseEntity.status(HttpStatus.OK).body(parentService.addParent(parentRegistrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponseProjection<AuthResponseProjection>> login(@Valid @RequestBody LoginRequestDto request) {
        log.info("Received POST request: Authentication attempt for User: {}", request.getEmail());

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        }
        catch(BadCredentialsException ex) {
            log.error("Login failed: Invalid credentials for user {}", request.getEmail());
            throw new SecurityException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        catch (DisabledException ex) {
            log.error("Login failed: User account {} is disabled", request.getEmail());
            throw new SecurityException("Account is disabled. Please contact admin.", HttpStatus.FORBIDDEN);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseProjection<>(true, "User logged in successfully", new AuthResponseProjection(jwt)));
    }
}
