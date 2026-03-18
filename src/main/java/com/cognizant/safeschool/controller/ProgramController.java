package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.dto.ProgramDto;
import com.cognizant.safeschool.service.IProgramService;
import com.cognizant.safeschool.projection.ProgramProjection;
import com.cognizant.safeschool.projection.SuccessResponseProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/program")
@Slf4j
public class ProgramController {
    @Autowired
    IProgramService programServiceImpl;

    @GetMapping("/")
    public ResponseEntity<SuccessResponseProjection<List<ProgramProjection>>> getAllPrograms(){
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.getAllPrograms());
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseProjection<ProgramProjection>> createProgram(@RequestBody ProgramDto programDto){
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.createProgram(programDto));
    }

    @GetMapping("/{programId}")
    public ResponseEntity<SuccessResponseProjection<ProgramProjection>> getProgram(@PathVariable Long programId){
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.getProgram(programId));
    }

    @PutMapping("/update/{programId}")
    public ResponseEntity<SuccessResponseProjection<ProgramProjection>> updateProgram(@PathVariable Long programId, @RequestBody  ProgramDto programDto){
        return ResponseEntity.status(HttpStatus.OK).body(programServiceImpl.updateProgram(programId, programDto));
    }
}
