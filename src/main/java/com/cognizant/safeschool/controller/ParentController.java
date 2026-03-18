package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.ParentDTO;
import com.cognizant.safeschool.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping("/register")
    public ResponseEntity<ParentDTO> registerParent(@RequestBody ParentDTO parentDTO) {
        ParentDTO createdParent = parentService.registerParent(parentDTO);
        return new ResponseEntity<>(createdParent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable Long id) throws ResourceNotFoundException {
        ParentDTO parent = parentService.getParentById(id);
        return ResponseEntity.ok(parent);
    }
}