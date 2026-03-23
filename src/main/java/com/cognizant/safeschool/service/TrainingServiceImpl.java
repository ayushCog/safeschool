package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ProgramException;
import com.cognizant.safeschool.classexception.TrainingException;
import com.cognizant.safeschool.classexception.UserException;
import com.cognizant.safeschool.dto.TrainingDto;
import com.cognizant.safeschool.entity.Program;
import com.cognizant.safeschool.entity.Training;
import com.cognizant.safeschool.entity.User;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.TrainingProjection;
import com.cognizant.safeschool.repository.ProgramRepository;
import com.cognizant.safeschool.repository.TrainingRepository;
import com.cognizant.safeschool.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TrainingServiceImpl implements ITrainingService{
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SuccessResponseProjection<TrainingProjection> enrollStaff(TrainingDto dto) {
        log.info("Service request: Enrolling Staff ID: {} into Program ID: {}", dto.getUserId(), dto.getProgramId());

        Program program = programRepository.findById(dto.getProgramId())
                .orElseThrow(() -> {
                    log.error("Enrollment failed: Program ID {} not found", dto.getProgramId());
                    return new ProgramException("Program not found", HttpStatus.NOT_FOUND);
                });

        User staff = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> {
                    log.error("Enrollment failed: User ID {} not found", dto.getUserId());
                    return new UserException("User not found", HttpStatus.NOT_FOUND);
                });

        Training training = new Training();
        training.setProgram(program);
        training.setStaff(staff);
        training.setStatus(dto.getStatus() != null ? dto.getStatus() : "ENROLLED");
        training.setCompletionDate(dto.getCompletionDate());

        Training savedTraining = trainingRepository.save(training);

        log.info("Successfully enrolled Staff: {} in Program: '{}'. Training ID: {}", staff.getEmail(), program.getTitle(), savedTraining.getTrainingId());

        return new SuccessResponseProjection<>(true, "Staff enrolled successfully", mapToProjection(savedTraining));
    }

    public SuccessResponseProjection<List<TrainingProjection>> getTrainingsByProgram(Long programId) {
        log.info("Service request: Retrieving all training records for Program ID: {}", programId);

        List<TrainingProjection> programTrainings = trainingRepository.findAllByProgramId(programId);

        log.info("Retrieved {} training records for Program ID: {}", programTrainings.size(), programId);
        return new SuccessResponseProjection<>(true, "Program trainings retrieved", programTrainings);
    }

    @Transactional
    public SuccessResponseProjection<TrainingProjection> updateTraining(Long trainingId, TrainingDto dto) {
        log.info("Service request: Updating Training ID: {} with Status: {}", trainingId, dto.getStatus());

        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> {
                    log.error("Update failed: Training record ID {} not found", trainingId);
                    return new TrainingException("Training record not found", HttpStatus.NOT_FOUND);
                });

        if (dto.getStatus() != null) {
            String oldStatus = training.getStatus();
            training.setStatus(dto.getStatus());

            if ("COMPLETED".equalsIgnoreCase(dto.getStatus()) && training.getCompletionDate() == null) {
                training.setCompletionDate(LocalDate.now());
                log.info("Training ID: {} marked as COMPLETED. Completion date set to today.", trainingId);
            }

            log.debug("Training ID: {} status changed from {} to {}", trainingId, oldStatus, dto.getStatus());
        }

        if (dto.getCompletionDate() != null) {
            training.setCompletionDate(dto.getCompletionDate());
        }

        Training updatedTraining = trainingRepository.save(training);

        log.info("Successfully updated Training ID: {}", trainingId);
        return new SuccessResponseProjection<>(true, "Training updated successfully", mapToProjection(updatedTraining));
    }

    public SuccessResponseProjection<List<TrainingProjection>> getUserTrainings(Long userId) {
        log.info("Service request: Fetching complete training history for Staff ID: {}", userId);

        List<TrainingProjection> userTrainings = trainingRepository.findAllByUserId(userId);

        log.info("Retrieved {} training records for User ID: {}", userTrainings.size(), userId);
        return new SuccessResponseProjection<>(true, "Staff training history retrieved", userTrainings);
    }

    private TrainingProjection mapToProjection(Training t) {
        return new TrainingProjection(
                t.getTrainingId(),
                t.getProgram().getProgramId(),
                t.getStaff().getUserId(),
                t.getCompletionDate(),
                t.getStatus()
        );
    }
}
