package com.project.ms_idtech.dto.course;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CourseCreateRequest(
        @NotBlank String title,
        String description,
        Set<Long> teacherIds
) { }