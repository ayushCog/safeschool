package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.ComplianceRecordRequestDTO;
import com.cognizant.safeschool.projection.ComplianceProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.service.IComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance")
public class ComplianceController {

    @Autowired
    private IComplianceService complianceServiceImpl;

    @PostMapping("/log")
    public ResponseEntity<SuccessResponseProjection<ComplianceProjection>> logCompliance(@RequestBody ComplianceRecordRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.OK).body(complianceServiceImpl.saveComplianceFromDto(dto));
    }
}