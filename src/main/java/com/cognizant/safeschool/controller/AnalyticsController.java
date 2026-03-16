package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PostMapping("/generate-report")
    public ResponseEntity<String> triggerReport(@RequestParam String scope) {
        analyticsService.generateReportFromBackend(scope);
        return ResponseEntity.ok("Report generated successfully from backend data.");
    }
}