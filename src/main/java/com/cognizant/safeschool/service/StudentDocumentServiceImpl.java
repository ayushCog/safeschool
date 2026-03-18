package com.cognizant.safeschool.service;

import com.cognizant.safeschool.classexception.ResourceNotFoundException;
import com.cognizant.safeschool.dto.StudentDocumentDTO;
import com.cognizant.safeschool.entity.StudentDocument;
import com.cognizant.safeschool.repository.StudentDocumentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentDocumentServiceImpl implements StudentDocumentService {

    private final StudentDocumentRepo documentRepo;

    @Override
    public StudentDocumentDTO uploadDocument(StudentDocumentDTO documentDTO) {
        StudentDocument document = new StudentDocument();
        document.setUploadedDate(LocalDate.now());
        // TODO: Map documentDTO fields to document entity
        StudentDocument savedDocument = documentRepo.save(document);
        // TODO: Map saved entity back to DTO
        return documentDTO;
    }

    @Override
    public StudentDocumentDTO getDocumentById(Long id) throws ResourceNotFoundException {
        StudentDocument document = documentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));

        StudentDocumentDTO dto = new StudentDocumentDTO();
        dto.setDocumentId(document.getDocumentId());
        // TODO: Map remaining fields
        return dto;
    }

    @Override
    public List<StudentDocumentDTO> getDocumentsByStudentId(Long studentId) {
        return List.of(); // TODO: Implement fetching documents by student ID
    }
}
