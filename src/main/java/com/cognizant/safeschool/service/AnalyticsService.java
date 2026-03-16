package com.cognizant.safeschool.service;

import com.cognizant.safeschool.entity.Report;
import com.cognizant.safeschool.repository.ReportRepository;
import com.cognizant.safeschool.repository.ComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AnalyticsService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ComplianceRepository complianceRepository;

    public void generateReportFromBackend(String scope) {
        long count = complianceRepository.count();
        String summaryMetrics = "Total Compliance Records Tracked: " + count;

        Report report = new Report(null, scope, summaryMetrics, LocalDateTime.now());
        reportRepository.save(report);
        System.out.println("Analytics Module: Internal Report Generated for " + scope);
    }
}