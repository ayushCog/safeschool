package com.cognizant.safeschool.service;

import com.cognizant.safeschool.entity.*;
import com.cognizant.safeschool.dto.ComplianceRecordRequestDTO;
import com.cognizant.safeschool.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ComplianceService {
    @Autowired private ComplianceRepository complianceRepo;
    @Autowired private AuditLogRepository logRepo;
    @Autowired private UserRepository userRepo;


    public void saveComplianceFromDto(ComplianceRecordRequestDTO dto) {
        ComplianceRecord record = new ComplianceRecord();
        record.setEntityId(dto.getEntityId());
        record.setType(dto.getType());
        record.setResult(dto.getResult());
        record.setNotes(dto.getNotes());
        record.setDate(LocalDate.now());

        User officer = userRepo.findById(dto.getOfficerId())
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        saveCompliance(record, officer);
    }

    public void saveCompliance(ComplianceRecord record, User officer) {
        complianceRepo.save(record);
        AuditLog log = new AuditLog(null, officer, "CREATE_COMPLIANCE", "Type: " + record.getType(), LocalDateTime.now());
        logRepo.save(log);
    }
}