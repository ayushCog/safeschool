package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.ParentResgistrationDto;
import com.cognizant.safeschool.projection.ParentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

public interface IParentService {
    public SuccessResponseProjection<ParentProjection> addParent(ParentResgistrationDto parentResgistrationDto);
}
