package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.ResolutionDto;
import com.cognizant.safeschool.projection.ResolutionProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

import java.util.List;

public interface IResolutionService {
    public SuccessResponseProjection<ResolutionProjection> createResolution(ResolutionDto resolutionDto);

    public SuccessResponseProjection<ResolutionProjection> getResolutionByIncident(Long incidentId);

    public SuccessResponseProjection<ResolutionProjection> updateResolution(Long resolutionId, ResolutionDto resolutionDto);

    public SuccessResponseProjection<List<ResolutionProjection>> getUserResolution(Long userId);
}
