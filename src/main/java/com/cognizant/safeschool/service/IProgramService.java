package com.cognizant.safeschool.service;

import com.cognizant.safeschool.dto.ProgramDto;
import com.cognizant.safeschool.projection.ProgramProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;

import java.util.List;

public interface IProgramService {
    public SuccessResponseProjection<ProgramProjection> createProgram(ProgramDto programDto);

    public SuccessResponseProjection<List<ProgramProjection>> getAllPrograms();

    public SuccessResponseProjection<ProgramProjection> updateProgram(Long programId, ProgramDto programDto);

    SuccessResponseProjection<ProgramProjection> getProgram(Long programId);
}
