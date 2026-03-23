package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ProgramException;
import com.cognizant.safeschool.classexception.TrainingException;
import com.cognizant.safeschool.dto.TrainingDto;
import com.cognizant.safeschool.entity.Program;
import com.cognizant.safeschool.entity.Training;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.TrainingProjection;
import com.cognizant.safeschool.repository.ProgramRepository;
import com.cognizant.safeschool.repository.TrainingRepository;
import com.cognizant.safeschool.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {
    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    public void enrollStaff_ValidTest() {
        TrainingDto dto = new TrainingDto();
        dto.setProgramId(1L);
        dto.setUserId(10L);

        Program program = new Program();
        program.setProgramId(1L);
        User user = new User();
        user.setUserId(10L);

        Training savedTraining = new Training();
        savedTraining.setTrainingId(100L);
        savedTraining.setProgram(program);
        savedTraining.setStaff(user);
        savedTraining.setStatus("ENROLLED");

        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(trainingRepository.save(any(Training.class))).thenReturn(savedTraining);

        SuccessResponseProjection<TrainingProjection> response = trainingService.enrollStaff(dto);

        assertTrue(response.isSuccess());
        assertEquals("ENROLLED", response.getData().getStatus());
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    public void enrollStaff_ProgramNotFound_ThrowsException() {
        TrainingDto dto = new TrainingDto();
        dto.setProgramId(99L);
        when(programRepository.findById(99L)).thenReturn(Optional.empty());

        ProgramException exception = assertThrows(ProgramException.class, () -> {
            trainingService.enrollStaff(dto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(trainingRepository, never()).save(any());
    }

    @Test
    public void updateTraining_SetCompleted_AutoSetsDate() {
        Long trainingId = 100L;
        TrainingDto dto = new TrainingDto();
        dto.setStatus("COMPLETED");

        Training existingTraining = new Training();
        existingTraining.setTrainingId(trainingId);
        existingTraining.setStatus("ENROLLED");
        existingTraining.setCompletionDate(null);
        existingTraining.setProgram(new Program());
        existingTraining.setStaff(new User());

        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(existingTraining));
        when(trainingRepository.save(any(Training.class))).thenReturn(existingTraining);

        SuccessResponseProjection<TrainingProjection> response = trainingService.updateTraining(trainingId, dto);

        assertTrue(response.isSuccess());
        assertEquals("COMPLETED", response.getData().getStatus());
        assertNotNull(response.getData().getCompletionDate());
        assertEquals(LocalDate.now(), response.getData().getCompletionDate());
    }

    @Test
    public void updateTraining_NotFound_ThrowsException() {
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TrainingException.class, () -> {
            trainingService.updateTraining(1L, new TrainingDto());
        });
    }

    @Test
    public void getTrainingsByProgram_ValidTest() {
        Long programId = 1L;
        TrainingProjection p = new TrainingProjection(100L, programId, 10L, LocalDate.now(), "COMPLETED");
        when(trainingRepository.findAllByProgramId(programId)).thenReturn(Arrays.asList(p));

        SuccessResponseProjection<List<TrainingProjection>> response = trainingService.getTrainingsByProgram(programId);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        verify(trainingRepository, times(1)).findAllByProgramId(programId);
    }

    @Test
    public void getUserTrainings_ValidTest() {
        Long userId = 10L;
        TrainingProjection p = new TrainingProjection(100L, 1L, userId, null, "ENROLLED");
        when(trainingRepository.findAllByUserId(userId)).thenReturn(Arrays.asList(p));

        SuccessResponseProjection<List<TrainingProjection>> response = trainingService.getUserTrainings(userId);

        assertTrue(response.isSuccess());
        assertEquals(userId, response.getData().get(0).getUserId());
    }
}
