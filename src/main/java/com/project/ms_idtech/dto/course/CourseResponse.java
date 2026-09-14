package com.project.ms_idtech.dto.course;

import com.project.ms_idtech.model.CourseStatus;
import com.project.ms_idtech.model.RecordStatus;

import java.util.Set;

public record CourseResponse(
        Long id,
        String title,
        String description,
        CourseStatus courseStatus,
        RecordStatus recordStatus,
        Set<TeacherMini> teachers
) {
    public record TeacherMini(Long id, String firstName, String lastName, String email) {}
}