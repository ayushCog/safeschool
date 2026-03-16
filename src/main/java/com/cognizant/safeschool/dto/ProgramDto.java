package com.cognizant.safeschool.dto;

import com.cognizant.safeschool.entity.Program.ProgramStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ProgramCreateRequest {
    @NotBlank @Size(max = 200)
    private String title;

    @Size(max = 4000)
    private String description;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private ProgramStatus status;

    // getters/setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public ProgramStatus getStatus() { return status; }
    public void setStatus(ProgramStatus status) { this.status = status; }
}

public class ProgramResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProgramStatus status;

    public ProgramResponse(Long id, String title, String description,
                           LocalDate startDate, LocalDate endDate, ProgramStatus status) {
        this.id = id; this.title = title; this.description = description;
        this.startDate = startDate; this.endDate = endDate; this.status = status;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public ProgramStatus getStatus() { return status; }
}
