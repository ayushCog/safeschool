package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplianceRepository extends JpaRepository<ComplianceRecord, Long> {
}