package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.ComplianceRecordDto;
import com.cognizant.safeschool.entity.ComplianceRecord;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.ComplianceProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.ComplianceRepository;
import com.cognizant.safeschool.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComplianceServiceTest {
    @Mock
    private ComplianceRepository complianceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ComplianceServiceImpl complianceService;

    @Test
    public void saveComplianceFromDto_ValidTest() {
        Long officerId = 1L;
        ComplianceRecordDto dto = new ComplianceRecordDto(
                101L, "FIRE_SAFETY", "PASSED", "Clear", officerId, LocalDate.now()
        );

        User officer = new User();
        officer.setUserId(officerId);
        officer.setEmail("officer@school.com");

        ComplianceRecord savedRecord = new ComplianceRecord();
        savedRecord.setComplianceId(50L);
        savedRecord.setEntityId(101L);
        savedRecord.setResult("PASSED");

        when(userRepository.findById(officerId)).thenReturn(Optional.of(officer));

        when(complianceRepository.save(ArgumentMatchers.<ComplianceRecord>any())).thenReturn(savedRecord);

        SuccessResponseProjection<ComplianceProjection> response = complianceService.saveComplianceFromDto(dto);

        assertTrue(response.isSuccess());
        assertEquals("Compliance record logged successfully", response.getMessage());
        assertEquals(50L, response.getData().getComplianceId());
    }

    @Test
    public void saveComplianceFromDto_InvalidOfficerTest() {
        Long invalidOfficerId = 99L;
        ComplianceRecordDto dto = new ComplianceRecordDto();
        dto.setOfficerId(invalidOfficerId);

        when(userRepository.findById(invalidOfficerId)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> {
            complianceService.saveComplianceFromDto(dto);
        });

        assertEquals("Officer not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    public void getAllComplianceRecords_ValidTest() {
        ComplianceProjection cp = new ComplianceProjection(
                50L, 101L, "FIRE_SAFETY", "PASSED", LocalDate.now(), "Clear"
        );

        when(complianceRepository.getAllComplianceRecords()).thenReturn(Arrays.asList(cp));

        SuccessResponseProjection<List<ComplianceProjection>> response = complianceService.getAllComplianceRecords();

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals("FIRE_SAFETY", response.getData().get(0).getType());
        verify(complianceRepository, times(1)).getAllComplianceRecords();
    }

    @Test
    public void getAllComplianceRecords_EmptyDataTest() {
        when(complianceRepository.getAllComplianceRecords()).thenReturn(Collections.emptyList());

        SuccessResponseProjection<List<ComplianceProjection>> response = complianceService.getAllComplianceRecords();

        assertTrue(response.isSuccess());
        assertTrue(response.getData().isEmpty());
        assertEquals("Compliance records retrieved successfully", response.getMessage());
    }
}
