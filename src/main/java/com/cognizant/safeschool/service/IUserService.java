package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.UserResgistrationDto;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.UserProjection;

public interface IUserService {
    public SuccessResponseProjection<UserProjection> addUser(UserResgistrationDto userRegistrationDto);
}
