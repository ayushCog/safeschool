package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.entity.Emergency;
import com.cognizant.safeschool.entity.Drill;
import com.cognizant.safeschool.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @PostMapping("/report")
    public ResponseEntity<Emergency> createEmergency(@RequestBody Emergency emergency) {
        return ResponseEntity.ok(emergencyService.reportEmergency(emergency));
    }

    @GetMapping("/all")
    public List<Emergency> getAllEmergencies() {
        return emergencyService.getAllEmergencies();
    }

    @PostMapping("/{emergencyId}/drills/schedule")
    public ResponseEntity<Drill> scheduleDrill(
            @PathVariable Long emergencyId,
            @RequestBody Drill drill) {

        // We pass the ID from the URL and the Drill data from the Body
        Drill savedDrill = emergencyService.scheduleDrill(emergencyId, drill);
        return ResponseEntity.ok(savedDrill);
    }

    @GetMapping("/drills")
    public List<Drill> getAllDrills() {
        return emergencyService.getAllDrills();
    }
}