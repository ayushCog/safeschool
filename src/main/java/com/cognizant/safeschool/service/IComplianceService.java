package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.ComplianceRecordRequestDTO;
import com.cognizant.safeschool.projection.ComplianceProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

import java.util.List;

public interface IComplianceService {

    public SuccessResponseProjection<List<ComplianceProjection>> getAllComplianceRecords();

    public SuccessResponseProjection<List<ComplianceProjection>> getComplianceByEntityId(Long entityId);

    public SuccessResponseProjection<ComplianceProjection> saveComplianceFromDto(ComplianceRecordRequestDTO dto);
}