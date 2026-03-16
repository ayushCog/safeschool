package com.cognizant.safeschool.dto;

import com.cognizant.safeschool.entity.Training.TrainingStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class TrainingCreateRequest {
    @NotNull
    private Long programId;

    // completionDate is optional; must be set if status=COMPLETED (service enforces)
    private LocalDate completionDate;

    @NotNull
    private TrainingStatus status;

    // getters/setters
    public Long getProgramId() { return programId; }
    public void setProgramId(Long programId) { this.programId = programId; }
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    public TrainingStatus getStatus() { return status; }
    public void setStatus(TrainingStatus status) { this.status = status; }
}

public class TrainingResponse {
    private Long id;
    private Long programId;
    private LocalDate completionDate;
    private TrainingStatus status;

    public TrainingResponse(Long id, Long programId, LocalDate completionDate, TrainingStatus status) {
        this.id = id; this.programId = programId; this.completionDate = completionDate; this.status = status;
    }

    public Long getId() { return id; }
    public Long getProgramId() { return programId; }
    public LocalDate getCompletionDate() { return completionDate; }
    public TrainingStatus getStatus() { return status; }
}
``
