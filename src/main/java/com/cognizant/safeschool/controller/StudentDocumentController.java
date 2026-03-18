package com.cognizant.safeschool.controller;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.StudentDocumentDTO;
import com.cognizant.safeschool.service.StudentDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class StudentDocumentController {

    private final StudentDocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<StudentDocumentDTO> uploadDocument(@RequestBody StudentDocumentDTO documentDTO) {
        StudentDocumentDTO savedDocument = documentService.uploadDocument(documentDTO);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDocumentDTO> getDocumentById(@PathVariable Long id) throws ResourceNotFoundException {
        StudentDocumentDTO document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentDocumentDTO>> getDocumentsByStudentId(@PathVariable Long studentId) {
        List<StudentDocumentDTO> documents = documentService.getDocumentsByStudentId(studentId);
        return ResponseEntity.ok(documents);
    }
}