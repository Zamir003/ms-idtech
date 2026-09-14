package com.project.ms_idtech.dto.teacher;

import com.project.ms_idtech.model.RecordStatus;

import java.time.LocalDate;

public record TeacherResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        RecordStatus status
) { }