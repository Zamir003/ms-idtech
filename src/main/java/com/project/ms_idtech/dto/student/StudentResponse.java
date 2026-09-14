package com.project.ms_idtech.dto.student;

import com.project.ms_idtech.model.RecordStatus;

import java.time.LocalDate;

public record StudentResponse(
        Long id,
        String fullName,
        String email,
        LocalDate birthDate,
        RecordStatus status
) { }