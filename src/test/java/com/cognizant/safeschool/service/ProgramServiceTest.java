package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ProgramException;
import com.cognizant.safeschool.dto.ProgramDto;
import com.cognizant.safeschool.entity.Program;
import com.cognizant.safeschool.projection.ProgramProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.ProgramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {
    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private ProgramServiceImpl programService;

    @Test
    public void createProgram_ValidTest() {
        ProgramDto dto = new ProgramDto();
        dto.setTitle("First Aid Training");
        dto.setStatus("ACTIVE");

        Program savedProgram = new Program();
        savedProgram.setProgramId(1L);
        savedProgram.setTitle("First Aid Training");
        savedProgram.setStatus("ACTIVE");

        when(programRepository.save(any(Program.class))).thenReturn(savedProgram);

        SuccessResponseProjection<ProgramProjection> response = programService.createProgram(dto);

        assertTrue(response.isSuccess());
        assertEquals("First Aid Training", response.getData().getTitle());
        verify(programRepository, times(1)).save(any(Program.class));
    }

    @Test
    public void getAllPrograms_ValidTest() {
        ProgramProjection p1 = new ProgramProjection(1L, "Fire Safety", "Desc", null, null, "ACTIVE");
        when(programRepository.getAllPrograms()).thenReturn(Arrays.asList(p1));

        SuccessResponseProjection<List<ProgramProjection>> response = programService.getAllPrograms();

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals("Fire Safety", response.getData().get(0).getTitle());
    }

    @Test
    public void updateProgram_ValidTest() {
        Long id = 1L;
        ProgramDto dto = new ProgramDto();
        dto.setTitle("Updated Title");

        Program existingProgram = new Program();
        existingProgram.setProgramId(id);
        existingProgram.setTitle("Old Title");

        when(programRepository.findById(id)).thenReturn(Optional.of(existingProgram));
        when(programRepository.save(any(Program.class))).thenReturn(existingProgram);

        SuccessResponseProjection<ProgramProjection> response = programService.updateProgram(id, dto);

        assertTrue(response.isSuccess());
        assertEquals("Updated Title", response.getData().getTitle());
    }

    @Test
    public void updateProgram_NotFound_ThrowsException() {
        Long id = 99L;
        when(programRepository.findById(id)).thenReturn(Optional.empty());

        ProgramException exception = assertThrows(ProgramException.class, () -> {
            programService.updateProgram(id, new ProgramDto());
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertTrue(exception.getMessage().contains("Program not found"));
    }

    @Test
    public void getProgram_ValidTest() {
        Long id = 1L;
        Program program = new Program();
        program.setProgramId(id);
        program.setTitle("Earthquake Drill");

        when(programRepository.findById(id)).thenReturn(Optional.of(program));

        SuccessResponseProjection<ProgramProjection> response = programService.getProgram(id);

        assertTrue(response.isSuccess());
        assertEquals(id, response.getData().getProgramId());
    }
}
