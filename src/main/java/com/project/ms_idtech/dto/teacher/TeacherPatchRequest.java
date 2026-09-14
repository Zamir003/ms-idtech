package com.project.ms_idtech.dto.teacher;

import com.project.ms_idtech.model.RecordStatus;

import java.time.LocalDate;

public record TeacherPatchRequest(
        String firstName,
        String lastName,
        LocalDate birthDate,
        RecordStatus status
) { }