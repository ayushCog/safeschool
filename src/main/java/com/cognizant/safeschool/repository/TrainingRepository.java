package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Training;
import com.cognizant.safeschool.projection.TrainingProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT new com.cognizant.safeschool.projection.TrainingProjection(t.trainingId, t.program.programId, t.staff.userId, t.completionDate, t.status) FROM Training t WHERE t.program.programId = :programId")
    List<TrainingProjection> findAllByProgramId(@Param("programId") Long programId);

    @Query("SELECT new com.cognizant.safeschool.projection.TrainingProjection(t.trainingId, t.program.programId, t.staff.userId, t.completionDate, t.status) FROM Training t WHERE t.staff.userId = :userId")
    List<TrainingProjection> findAllByUserId(@Param("userId") Long userId);
}
