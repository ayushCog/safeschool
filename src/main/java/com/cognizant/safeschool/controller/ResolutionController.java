package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.ResolutionDto;
import com.cognizant.safeschool.projection.ResolutionProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.service.IResolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resolution")
public class ResolutionController {
    @Autowired
    IResolutionService resolutionServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<ResolutionProjection>> createResolution(@RequestBody ResolutionDto resolutionDto){
        return ResponseEntity.status(HttpStatus.OK).body(resolutionServiceImpl.createResolution(resolutionDto));
    }

    @GetMapping("/get/{incidentId}")
    public ResponseEntity<SuccessResponseProjection<ResolutionProjection>> getResolutionByIncident(@PathVariable Long incidentId){
        return ResponseEntity.status(HttpStatus.OK).body(resolutionServiceImpl.getResolutionByIncident(incidentId));
    }

    @PutMapping("/update/{resolutionId}")
    public ResponseEntity<SuccessResponseProjection<ResolutionProjection>> updateResolution(@PathVariable Long resolutionId, @RequestBody ResolutionDto resolutionDto){
        return ResponseEntity.status(HttpStatus.OK).body(resolutionServiceImpl.updateResolution(resolutionId, resolutionDto));
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<ResolutionProjection>>> getUserResolution(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(resolutionServiceImpl.getUserResolution(userId));
    }
}
