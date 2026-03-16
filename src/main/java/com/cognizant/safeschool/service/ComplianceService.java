package com.cognizant.safeschool.service;

import com.cognizant.safeschool.entity.ComplianceRecord;
import com.cognizant.safeschool.entity.AuditLog;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.repository.ComplianceRepository;
import com.cognizant.safeschool.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ComplianceService {
    @Autowired
    private ComplianceRepository complianceRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void saveCompliance(ComplianceRecord record, User officerId) {
        complianceRepository.save(record);

        AuditLog log = new AuditLog(null, officerId, "CREATE_COMPLIANCE", "RecordType: " + record.getType(), LocalDateTime.now());
        auditLogRepository.save(log);
    }
}