package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ProgramException;
import com.cognizant.safeschool.dto.ProgramDto;
import com.cognizant.safeschool.entity.Program;
import com.cognizant.safeschool.projection.ProgramProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import com.cognizant.safeschool.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramServiceImpl implements IProgramService {
    @Autowired
    ProgramRepository programRepository;

    @Transactional
    public SuccessResponseProjection<ProgramProjection> createProgram(ProgramDto programDto) {
        Program program = new Program();
        program.setTitle(programDto.getTitle());
        program.setDescription(programDto.getDescription());
        program.setStartDate(programDto.getStartDate());
        program.setEndDate(programDto.getEndDate());
        program.setStatus(programDto.getStatus() != null ? programDto.getStatus() : "ACTIVE");

        Program savedProgram = programRepository.save(program);
        return new SuccessResponseProjection<>(true, "Program created successfully", mapToProjection(savedProgram));
    }

    public SuccessResponseProjection<List<ProgramProjection>> getAllPrograms() {
        List<ProgramProjection> programs = programRepository.getAllPrograms();

        return new SuccessResponseProjection<>(true, "Programs retrieved successfully", programs);
    }

    @Transactional
    public SuccessResponseProjection<ProgramProjection> updateProgram(Long programId, ProgramDto programDto) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramException("Program not found with id: " + programId, HttpStatus.NOT_FOUND));

        if(programDto.getTitle() != null){
            program.setTitle(programDto.getTitle());
        }

        if(programDto.getDescription() != null){
            program.setDescription(programDto.getDescription());
        }

        if(programDto.getStartDate() != null){
            program.setStartDate(programDto.getStartDate());
        }

        if(programDto.getEndDate() != null){
            program.setEndDate(programDto.getEndDate());
        }

        if(programDto.getStatus() != null){
            program.setStatus(programDto.getStatus());
        }

        Program updatedProgram = programRepository.save(program);
        return new SuccessResponseProjection<>(true, "Program updated successfully", mapToProjection(updatedProgram));
    }

    public SuccessResponseProjection<ProgramProjection> getProgram(Long programId) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ProgramException("Program not found with id: " + programId, HttpStatus.NOT_FOUND));

        return new SuccessResponseProjection<>(true, "Program retrieved successfully", mapToProjection(program));
    }

    private ProgramProjection mapToProjection(Program program) {
        return new ProgramProjection(
                program.getProgramId(),
                program.getTitle(),
                program.getDescription(),
                program.getStartDate(),
                program.getEndDate(),
                program.getStatus()
        );
    }
}
