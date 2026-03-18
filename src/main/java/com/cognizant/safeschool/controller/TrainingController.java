package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.TrainingDto;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.projection.TrainingProjection;
import com.cognizant.safeschool.service.ITrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {
    @Autowired
    private ITrainingService trainingServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<TrainingProjection>> enrollStaff(@RequestBody TrainingDto trainingDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingServiceImpl.enrollStaff(trainingDto));
    }

    @GetMapping("/program/{programId}")
    public ResponseEntity<SuccessResponseProjection<List<TrainingProjection>>> getTrainingsByProgram(@PathVariable Long programId) {
        return ResponseEntity.status(HttpStatus.OK).body(trainingServiceImpl.getTrainingsByProgram(programId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<SuccessResponseProjection<List<TrainingProjection>>> getUserTrainings(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(trainingServiceImpl.getUserTrainings(userId));
    }

    @PutMapping("/update/{trainingId}")
    public ResponseEntity<SuccessResponseProjection<TrainingProjection>> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        return ResponseEntity.status(HttpStatus.OK).body(trainingServiceImpl.updateTraining(trainingId, trainingDto));
    }
}
