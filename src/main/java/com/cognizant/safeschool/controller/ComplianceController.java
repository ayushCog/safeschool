package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.ComplianceRecordRequestDTO;
import com.cognizant.safeschool.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compliance")
@CrossOrigin(origins = "*")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

    @PostMapping("/log")
    public ResponseEntity<String> logCompliance(@RequestBody ComplianceRecordRequestDTO dto) {
        complianceService.saveComplianceFromDto(dto);
        return ResponseEntity.ok("Compliance record logged and audit trail created.");
    }
}