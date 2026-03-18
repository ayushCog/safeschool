package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.IncidentDto;
import com.cognizant.safeschool.projection.IncidentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

import java.util.List;

public interface IIncidentService {
    public SuccessResponseProjection<List<IncidentProjection>> getAllIncidents();
    public SuccessResponseProjection<List<IncidentProjection>> getUserIncidents(Long userId);
    public SuccessResponseProjection<IncidentProjection> createIncident(IncidentDto incidentDto);
}
