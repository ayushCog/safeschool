package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.UserResgistrationDto;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.UserProjection;
import com.cognizant.safeschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserRepository userRepository;

    public SuccessResponseProjection<UserProjection> addUser(UserResgistrationDto userResgistrationDto) throws UserException {
        if(userRepository.findUserByEmail(userResgistrationDto.getEmail()) != null){
            throw new UserException("User already registered", HttpStatus.FOUND);
        }

        User user=new User();
        user.setName(userResgistrationDto.getName());
        user.setRole(userResgistrationDto.getRole());
        user.setEmail(userResgistrationDto.getEmail());
        user.setPhone(userResgistrationDto.getPhone());
        user.setStatus(userResgistrationDto.getStatus());
        user.setPassword(userResgistrationDto.getPassword());

        User savedUser=userRepository.save(user);
        return new SuccessResponseProjection<>(true, "User created successfully", new UserProjection(savedUser.getName(), savedUser.getRole(), savedUser.getEmail(), savedUser.getPhone(), savedUser.getStatus()));
    }
}
