package com.cognizant.safeschool.repository;

import com.cognizant.safeschool.entity.Program;
import com.cognizant.safeschool.projection.ProgramProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("select new com.cognizant.safeschool.projection.ProgramProjection(p.programId, p.title, p.description, p.startDate, p.endDate, p.status) from Program p")
    List<ProgramProjection> getAllPrograms();
}
