package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.entity.ComplianceRecord;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

    @PostMapping("/create")
    public ResponseEntity<String> createCompliance(@RequestBody ComplianceRecord record, @RequestParam User officerId) {
        complianceService.saveCompliance(record, officerId);
        return ResponseEntity.ok("Compliance record created and logged.");
    }
}