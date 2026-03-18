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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainingServiceImpl implements ITrainingService{
    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SuccessResponseProjection<TrainingProjection> enrollStaff(TrainingDto dto) {
        Program program = programRepository.findById(dto.getProgramId())
                .orElseThrow(() -> new ProgramException("Program not found", HttpStatus.NOT_FOUND));

        User staff = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserException("User not found", HttpStatus.NOT_FOUND));

        Training training = new Training();
        training.setProgram(program);
        training.setStaff(staff);
        training.setStatus(dto.getStatus() != null ? dto.getStatus() : "ENROLLED");
        training.setCompletionDate(dto.getCompletionDate());

        Training savedTraining = trainingRepository.save(training);
        return new SuccessResponseProjection<>(true, "Staff enrolled successfully", mapToProjection(savedTraining));
    }

    public SuccessResponseProjection<List<TrainingProjection>> getTrainingsByProgram(Long programId) {
        List<TrainingProjection> programs = trainingRepository.findAllByProgramId(programId);
        return new SuccessResponseProjection<>(true, "Program trainings retrieved", programs);
    }

    @Transactional
    public SuccessResponseProjection<TrainingProjection> updateTraining(Long trainingId, TrainingDto dto) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingException("Training record not found", HttpStatus.NOT_FOUND));

        if (dto.getStatus() != null) {
            training.setStatus(dto.getStatus());

            if ("COMPLETED".equalsIgnoreCase(dto.getStatus()) && training.getCompletionDate() == null) {
                training.setCompletionDate(LocalDate.now());
            }
        }

        if (dto.getCompletionDate() != null) {
            training.setCompletionDate(dto.getCompletionDate());
        }

        Training updatedTraining = trainingRepository.save(training);
        return new SuccessResponseProjection<>(true, "Training updated successfully", mapToProjection(updatedTraining));
    }

    public SuccessResponseProjection<List<TrainingProjection>> getUserTrainings(Long userId) {
        List<TrainingProjection> userTrainings = trainingRepository.findAllByUserId(userId);
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
