package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.IncidentDto;
import com.cognizant.safeschool.projection.IncidentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.service.IIncidentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidents")
@Slf4j
public class IncidentController {
    @Autowired
    IIncidentService incidentServiceImpl;

    @PreAuthorize("isAuthenticated() && !hasAnyRole('STUDENT', 'PARENT')")
    @GetMapping("/")
    public ResponseEntity<SuccessResponseProjection<List<IncidentProjection>>> getAllIncidents(){
        log.info("Received GET request: Fetching all incidents");
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.getAllIncidents());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<IncidentProjection>>> getUserIncidents(@PathVariable Long userId){
        log.info("Received GET request: Fetching incidents for User ID: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.getUserIncidents(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<IncidentProjection>> createIncident(@Valid @RequestBody IncidentDto incidentDto){
        log.info("Received POST request: Creating new incident report by User ID: {}", incidentDto.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.createIncident(incidentDto));
    }
}
