package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.ParentRegistrationDto;
import com.cognizant.safeschool.projection.ParentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

public interface IParentService {
    public SuccessResponseProjection<ParentProjection> addParent(ParentRegistrationDto parentResgistrationDto);
}
