package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.ComplianceRecord;
import com.cognizant.safeschool.projection.ComplianceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRepository extends JpaRepository<ComplianceRecord, Long> {
    @Query("SELECT new com.cognizant.safeschool.projection.ComplianceProjection(c.complianceId, c.entityId, c.type, c.result, c.date, c.notes) FROM ComplianceRecord c")
    List<ComplianceProjection> getAllComplianceRecords();
}
