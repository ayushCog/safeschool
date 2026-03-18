package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.ParentDTO;

import java.util.List;

public interface ParentService {
    ParentDTO registerParent(ParentDTO parentDTO);
    ParentDTO getParentById(Long id) throws ResourceNotFoundException;
    List<ParentDTO> getAllParents();
}