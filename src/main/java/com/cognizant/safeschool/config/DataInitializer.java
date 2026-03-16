package com.cognizant.safeschool.config;

import com.cognizant.safeschool.entity.*;
import com.cognizant.safeschool.service.*;
import com.cognizant.safeschool.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import jakarta.annotation.Nonnull;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ComplianceService complianceService;
    private final AnalyticsService analyticsService;
    private final UserRepository userRepository;

    public DataInitializer(ComplianceService cs, AnalyticsService as, UserRepository ur) {
        this.complianceService = cs;
        this.analyticsService = as;
        this.userRepository = ur;
    }

    @Override
    public void run(@Nonnull String... args)  {

        User officer = new User();
        officer.setName("Officer John");
        officer.setRole("School Safety Officer");
        officer.setStatus("Active");
        User savedOfficer = userRepository.save(officer);

        ComplianceRecord drillRecord = new ComplianceRecord();
        drillRecord.setEntityId(500L);
        drillRecord.setType("Drill");
        drillRecord.setResult("Pass");
        drillRecord.setDate(LocalDate.now());
        drillRecord.setNotes("Backend initialized record.");

        complianceService.saveCompliance(drillRecord, savedOfficer);

        analyticsService.generateReportFromBackend("STARTUP_METRICS");

        System.out.println("SafeSchool Modules: Backend-driven data seeding complete.");
    }
}