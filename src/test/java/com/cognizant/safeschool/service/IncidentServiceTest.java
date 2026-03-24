package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.IncidentException;
import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.IncidentDto;
import com.cognizant.safeschool.entity.Incident;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.IncidentProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.IncidentRepository;
import com.cognizant.safeschool.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {
    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IncidentServiceImpl incidentService;

    @Test
    public void getAllIncidents_ValidTest() {
        IncidentProjection p1 = new IncidentProjection(1L, 101L, "Fire", "Lab", LocalDate.now(), "PENDING");
        List<IncidentProjection> list = Arrays.asList(p1);
        when(incidentRepository.getAllIncidents()).thenReturn(list);

        SuccessResponseProjection<List<IncidentProjection>> response = incidentService.getAllIncidents();

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        verify(incidentRepository, times(1)).getAllIncidents();
    }

    @Test
    public void getUserIncidents_ValidTest() {
        Long userId = 101L;
        IncidentProjection p1 = new IncidentProjection(1L, userId, "Bullying", "Gym", LocalDate.now(), "PENDING");
        when(incidentRepository.getUserIncidents(userId)).thenReturn(Arrays.asList(p1));

        SuccessResponseProjection<List<IncidentProjection>> response = incidentService.getUserIncidents(userId);

        assertTrue(response.isSuccess());
        assertEquals(userId, response.getData().get(0).getReporter());
    }

    @Test
    public void getUserIncidents_InvalidUserId_ThrowsException() {
        Long userId = 999L;
        when(incidentRepository.getUserIncidents(userId)).thenReturn(new ArrayList<>());

        IncidentException exception = assertThrows(IncidentException.class, () -> {
            incidentService.getUserIncidents(userId);
        });

        assertEquals("No user incidents found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }


    @Test
    public void createIncident_ValidTest() {
        IncidentDto dto = new IncidentDto();
        dto.setUserId(101L);
        dto.setType("Accident");
        dto.setLocation("Cafeteria");
        dto.setStatus("PENDING");

        User reporter = new User();
        reporter.setUserId(101L);

        Incident savedIncident = new Incident();
        savedIncident.setIncidentId(1L);
        savedIncident.setType(dto.getType());
        savedIncident.setReporter(reporter);

        when(userRepository.findById(101L)).thenReturn(Optional.of(reporter));
        when(incidentRepository.save(any(Incident.class))).thenReturn(savedIncident);

        SuccessResponseProjection<IncidentProjection> response = incidentService.createIncident(dto);

        assertTrue(response.isSuccess());
        assertEquals("Incident reported successfully", response.getMessage());
        assertNotNull(response.getData().getIncidentId());
    }

    @Test
    public void createIncident_UserNotFound_ThrowsException() {
        IncidentDto dto = new IncidentDto();
        dto.setUserId(999L);
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> {
            incidentService.createIncident(dto);
        });

        assertEquals("User not found", exception.getMessage());
        verify(incidentRepository, never()).save(any());
    }
}
