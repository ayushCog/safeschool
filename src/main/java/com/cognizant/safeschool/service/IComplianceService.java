package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.ComplianceRecordDto;
import com.cognizant.safeschool.projection.ComplianceProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

import java.util.List;

public interface IComplianceService {
    public SuccessResponseProjection<List<ComplianceProjection>> getAllComplianceRecords();

    public SuccessResponseProjection<ComplianceProjection> saveComplianceFromDto(ComplianceRecordDto dto);
}
