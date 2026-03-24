package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.TrainingDto;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.TrainingProjection;

import java.util.List;

public interface ITrainingService {
    public SuccessResponseProjection<TrainingProjection> enrollStaff(TrainingDto trainingDto);

    public SuccessResponseProjection<List<TrainingProjection>> getTrainingsByProgram(Long programId);

    public SuccessResponseProjection<List<TrainingProjection>> getUserTrainings(Long userId);

    public SuccessResponseProjection<TrainingProjection> updateTraining(Long trainingId, TrainingDto trainingDto);
}
