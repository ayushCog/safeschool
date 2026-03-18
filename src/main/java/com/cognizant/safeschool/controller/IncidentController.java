package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.IncidentDto;
import com.cognizant.safeschool.projection.IncidentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.service.IIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidents")
public class IncidentController {
    @Autowired
    IIncidentService incidentServiceImpl;

    @GetMapping("/")
    public ResponseEntity<SuccessResponseProjection<List<IncidentProjection>>> getAllIncidents(){
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.getAllIncidents());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<IncidentProjection>>> getUserIncidents(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.getUserIncidents(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<IncidentProjection>> createIncident(@RequestBody IncidentDto incidentDto){
        return ResponseEntity.status(HttpStatus.OK).body(incidentServiceImpl.createIncident(incidentDto));
    }
}
