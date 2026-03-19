package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ComplianceException;
import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.ComplianceRecordRequestDTO;
import com.cognizant.safeschool.entity.AuditLog;
import com.cognizant.safeschool.entity.ComplianceRecord;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.ComplianceProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.AuditLogRepository;
import com.cognizant.safeschool.repository.ComplianceRepository;
import com.cognizant.safeschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplianceServiceImpl implements IComplianceService {

    @Autowired
    private ComplianceRepository complianceRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public SuccessResponseProjection<List<ComplianceProjection>> getAllComplianceRecords() {
        List<ComplianceProjection> records = complianceRepository.getAllComplianceRecords();
        return new SuccessResponseProjection<>(true, "Compliance records retrieved successfully", records);
    }

    @Override
    public SuccessResponseProjection<List<ComplianceProjection>> getComplianceByEntityId(Long entityId) {
        List<ComplianceProjection> records = complianceRepository.getComplianceByEntityId(entityId);

        if (records.isEmpty()) {
            throw new ComplianceException("No compliance records found for this entity", HttpStatus.NOT_FOUND);
        }

        return new SuccessResponseProjection<>(true, "Entity compliance records retrieved successfully", records);
    }

    @Override
    @Transactional
    public SuccessResponseProjection<ComplianceProjection> saveComplianceFromDto(ComplianceRecordRequestDTO dto) {

        User officer = userRepository.findById(dto.getOfficerId())
                .orElseThrow(() -> new UserException("Officer not found", HttpStatus.NOT_FOUND));

        ComplianceRecord record = new ComplianceRecord();
        record.setEntityId(dto.getEntityId());
        record.setType(dto.getType());
        record.setResult(dto.getResult());
        record.setNotes(dto.getNotes());
        record.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());

        ComplianceRecord savedRecord = complianceRepository.save(record);

        AuditLog log = new AuditLog(null, officer, "CREATE_COMPLIANCE", "Type: " + savedRecord.getType(), LocalDateTime.now());
        auditLogRepository.save(log);

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