package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.ParentDTO;
import com.cognizant.safeschool.entity.Parent;
import com.cognizant.safeschool.repository.ParentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepo parentRepo;

    @Override
    public ParentDTO registerParent(ParentDTO parentDTO) {
        Parent parent = new Parent();
        // TODO: Map parentDTO fields to parent entity (user, student, relation, status)
        Parent savedParent = parentRepo.save(parent);
        // TODO: Map saved entity back to DTO
        return parentDTO;
    }

    @Override
    public ParentDTO getParentById(Long id) throws ResourceNotFoundException {
        Parent parent = parentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parent not found with ID: " + id));

        ParentDTO dto = new ParentDTO();
        dto.setParentId(parent.getParentId());
        // TODO: Map remaining fields
        return dto;
    }

    @Override
    public List<ParentDTO> getAllParents() {
        return List.of(); // TODO: Implement fetching and mapping all parents
    }
}