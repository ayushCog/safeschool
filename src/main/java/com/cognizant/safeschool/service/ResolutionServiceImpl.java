package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.IncidentException;
import com.cognizant.safeschool.classexception.ResolutionException;
import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.ResolutionDto;
import com.cognizant.safeschool.entity.Incident;
import com.cognizant.safeschool.entity.Resolution;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.ResolutionProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.IncidentRepository;
import com.cognizant.safeschool.repository.ResolutionRepository;
import com.cognizant.safeschool.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Slf4j
public class ResolutionServiceImpl implements IResolutionService{
    @Autowired
    private ResolutionRepository resolutionRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SuccessResponseProjection<ResolutionProjection> createResolution(ResolutionDto resolutionDto) {
        log.info("Service request: Initiating resolution creation for Incident ID: {}", resolutionDto.getIncidentId());

        Incident incident=incidentRepository.findById(resolutionDto.getIncidentId())
                .orElseThrow(() -> {
                    log.error("Resolution failed: Incident ID: {} not found", resolutionDto.getIncidentId());
                    return new IncidentException("Incident not found", HttpStatus.NOT_FOUND);
                });

        User officer=userRepository.findById(resolutionDto.getUserId())
                .orElseThrow(() -> {
                    log.error("Resolution failed: Officer ID: {} not found", resolutionDto.getUserId());
                    return new UserException("Officer not found", HttpStatus.NOT_FOUND);
                });

        Resolution resolution=new Resolution();
        resolution.setIncident(incident);
        resolution.setOfficer(officer);
        resolution.setActions(resolutionDto.getActions());
        resolution.setDate(resolutionDto.getDate());
        resolution.setStatus(resolutionDto.getStatus());

        incident.setStatus(resolutionDto.getStatus());

        Resolution savedRes=resolutionRepository.save(resolution);

        incidentRepository.save(incident);

        log.info("Successfully recorded Resolution ID: {} for Incident ID: {}. Incident status updated to: {}", savedRes.getResolutionId(), incident.getIncidentId(), incident.getStatus());

        return new SuccessResponseProjection<>(true, "Resolution recorded", mapToProjection(savedRes));
    }

    public SuccessResponseProjection<ResolutionProjection> getResolutionByIncident(Long incidentId) {
        log.info("Service request: Fetching resolution details for Incident ID: {}", incidentId);
        ResolutionProjection resolutionProjection=resolutionRepository.findResolutionByIncidentId(incidentId);

        if(resolutionProjection==null) {
            log.error("Fetch failed: No resolution exists for Incident ID: {}", incidentId);
            throw new ResolutionException("No resolution found for this incident", HttpStatus.NOT_FOUND);
        }

        log.info("Successfully retrieved resolution for Incident ID: {}", incidentId);

        return new SuccessResponseProjection<>(true, "Resolution retrieved successfully", resolutionProjection);
    }

    @Transactional
    public SuccessResponseProjection<ResolutionProjection> updateResolution(Long resolutionId, ResolutionDto resolutionDto) {
        log.info("Service request: Updating details for Resolution ID: {}", resolutionId);

        Resolution resolution=resolutionRepository.findById(resolutionId)
                .orElseThrow(() -> {
                    log.error("Update failed: Resolution ID: {} not found", resolutionId);
                    return new ResolutionException("Resolution not found", HttpStatus.NOT_FOUND);
                });

        if(resolutionDto.getActions() != null) {
            resolution.setActions(resolutionDto.getActions());
        }

        if(resolutionDto.getStatus() != null){
            resolution.setStatus(resolutionDto.getStatus());
            resolution.getIncident().setStatus(resolutionDto.getStatus());
        }

        Resolution updatedRes=resolutionRepository.save(resolution);

        log.info("Successfully updated Resolution ID: {}. New Status: {}", resolutionId, resolution.getStatus());
        return new SuccessResponseProjection<>(true, "Resolution updated", mapToProjection(updatedRes));
    }

    public SuccessResponseProjection<List<ResolutionProjection>> getUserResolution(Long userId){
        log.info("Service request: Retrieving resolution history for Officer ID: {}", userId);

        List<ResolutionProjection> resolutions=resolutionRepository.getUserResolution(userId);

        if(resolutions.isEmpty()){
            log.error("No resolutions found for Officer ID: {}", userId);
            throw new ResolutionException("Resolutions not found", HttpStatus.NOT_FOUND);
        }

        log.info("Successfully retrieved {} resolutions handled by Officer ID: {}", resolutions.size(), userId);

        return new SuccessResponseProjection<>(true, "User resolutions retrieved successfully", resolutions);
    }

    private ResolutionProjection mapToProjection(Resolution res) {
        return new ResolutionProjection(
                res.getResolutionId(),
                res.getIncident().getIncidentId(),
                res.getOfficer().getUserId(),
                res.getActions(),
                res.getDate(),
                res.getStatus()
        );
    }
}
