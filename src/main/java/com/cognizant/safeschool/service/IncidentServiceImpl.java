package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.IncidentException;
import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.IncidentDto;
import com.cognizant.safeschool.entity.Incident;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.IncidentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.IncidentRepository;
import com.cognizant.safeschool.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class IncidentServiceImpl implements IIncidentService {
    @Autowired
    IncidentRepository incidentRepository;

    @Autowired
    UserRepository userRepository;

    public SuccessResponseProjection<List<IncidentProjection>> getAllIncidents(){
        log.info("Service request: Retrieving full incident list from repository");

        List<IncidentProjection> incidents=incidentRepository.getAllIncidents();

        log.info("Successfully retrieved {} incidents", incidents.size());

        return new SuccessResponseProjection<>(true, "Incidents retrieved successfully", incidents);
    }

    public SuccessResponseProjection<List<IncidentProjection>> getUserIncidents(Long userId){
        log.info("Service request: Fetching incident history for User ID: {}", userId);

        List<IncidentProjection> incidents=incidentRepository.getUserIncidents(userId);

        if(incidents.isEmpty()){
            log.error("Incident fetch failed: No incidents found for User ID: {}", userId);
            throw new IncidentException("No user incidents found", HttpStatus.NOT_FOUND);
        }

        log.info("Successfully retrieved {} incidents for User ID: {}", incidents.size(), userId);

        return new SuccessResponseProjection<>(true, "User incidents retrieved successfully", incidents);
    }

    @Transactional
    public SuccessResponseProjection<IncidentProjection> createIncident(IncidentDto incidentDto){
        log.info("Service request: Initiating incident creation for User ID: {} type: {}", incidentDto.getUserId(), incidentDto.getType());

        User reporter=userRepository.findById(incidentDto.getUserId())
                .orElseThrow(() -> {
                    log.error("Incident creation failed: Reporter User ID: {} not found", incidentDto.getUserId());
                    return new UserException("User not found", HttpStatus.NOT_FOUND);
                });

        Incident incident=new Incident();
        incident.setType(incidentDto.getType());
        incident.setLocation(incidentDto.getLocation());
        incident.setDate(incidentDto.getDate() != null ? incidentDto.getDate() : LocalDate.now());
        incident.setStatus(incidentDto.getStatus());
        incident.setReporter(reporter);

        Incident savedIncident=incidentRepository.save(incident);

        IncidentProjection incidentProjection=new IncidentProjection(
                savedIncident.getIncidentId(),
                reporter.getUserId(),
                savedIncident.getType(),
                savedIncident.getLocation(),
                savedIncident.getDate(),
                savedIncident.getStatus()
        );

        log.info("Successfully saved Incident ID: {} reported by User ID: {}", savedIncident.getIncidentId(), reporter.getUserId());

        return new SuccessResponseProjection<>(true, "Incident reported successfully", incidentProjection);
    }
}
