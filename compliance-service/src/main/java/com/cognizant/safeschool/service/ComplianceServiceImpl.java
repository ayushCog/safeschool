package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.client.UserClient;
import com.cognizant.safeschool.dto.ComplianceRecordDto;
import com.cognizant.safeschool.dto.UserRegistrationDto;
import com.cognizant.safeschool.entity.ComplianceRecord;
import com.cognizant.safeschool.projection.ComplianceProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.ComplianceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ComplianceServiceImpl implements IComplianceService {
    @Autowired
    private ComplianceRepository complianceRepository;

    @Autowired
    private UserClient userClient;

    public SuccessResponseProjection<List<ComplianceProjection>> getAllComplianceRecords() {
        log.info("Service request: Retrieving all compliance projections from repository");
        List<ComplianceProjection> records = complianceRepository.getAllComplianceRecords();

        log.info("Successfully retrieved {} compliance records", records.size());
        return new SuccessResponseProjection<>(true, "Compliance records retrieved successfully", records);
    }

    @Transactional
    public SuccessResponseProjection<ComplianceProjection> saveComplianceFromDto(ComplianceRecordDto dto) {
        log.info("Service request: Initiating compliance save for Entity: {} Type: {}", dto.getEntityId(), dto.getType());

        UserRegistrationDto officer = userClient.getUserById(dto.getOfficerId());
        if (officer == null) {
            log.error("Compliance failed: Officer ID {} not found", dto.getOfficerId());
            throw new UserException("Officer not found", HttpStatus.NOT_FOUND);
        }

        ComplianceRecord record = new ComplianceRecord();
        record.setEntityId(dto.getEntityId());
        record.setType(dto.getType());
        record.setResult(dto.getResult());
        record.setNotes(dto.getNotes());
        record.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());

        ComplianceRecord savedRecord = complianceRepository.save(record);
        log.info("Compliance record persisted with ID: {}. Result: {}", savedRecord.getComplianceId(), savedRecord.getResult());

        ComplianceProjection projection = new ComplianceProjection(
                savedRecord.getComplianceId(),
                savedRecord.getEntityId(),
                savedRecord.getType(),
                savedRecord.getResult(),
                savedRecord.getDate(),
                savedRecord.getNotes()
        );

        return new SuccessResponseProjection<>(true, "Compliance record logged successfully", projection);
    }
}
