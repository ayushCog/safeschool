package com.cognizant.safeschool.service;

import com.cognizant.safeschool.entity.Emergency;
import com.cognizant.safeschool.entity.Drill;
import com.cognizant.safeschool.repository.EmergencyRepository;
import com.cognizant.safeschool.repository.DrillRepository;
import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import com.cognizant.safeschool.classexception.EmergencyException;
@Service
public class EmergencyService {
    @Autowired
    private EmergencyRepository emergencyRepo;

    @Autowired
    private DrillRepository drillRepo;

    public Emergency reportEmergency(Emergency emergency) {
        return emergencyRepo.save(emergency);
    }

    public List<Emergency> getAllEmergencies() {
        return emergencyRepo.findAll();
    }

    public Drill scheduleDrill(Long emergencyId, Drill drill) {

        Emergency emergency = emergencyRepo.findById(emergencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency not found with ID: " + emergencyId));


        if ("COMPLETED".equalsIgnoreCase(emergency.getStatus())) {
            throw new EmergencyException("Cannot schedule a drill for a completed emergency.", HttpStatus.BAD_REQUEST);
        }


        drill.setEmergency(emergency);


        return drillRepo.save(drill);
    }

    public List<Drill> getAllDrills() {
        return drillRepo.findAll();
    }
}