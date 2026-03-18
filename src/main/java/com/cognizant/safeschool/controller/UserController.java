package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.UserResgistrationDto;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.UserProjection;
import com.cognizant.safeschool.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponseProjection<UserProjection>> registerStudent(@RequestBody UserResgistrationDto userResgistrationDto) {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.addUser(userResgistrationDto));
    }
}
