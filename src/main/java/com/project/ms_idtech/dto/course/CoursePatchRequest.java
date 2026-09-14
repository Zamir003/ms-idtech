package com.project.ms_idtech.dto.course;

import com.project.ms_idtech.model.CourseStatus;
import com.project.ms_idtech.model.RecordStatus;

import java.util.Set;

public record CoursePatchRequest(
        String title,
        String description,
        CourseStatus courseStatus,
        RecordStatus recordStatus,
        Set<Long> teacherIds
) { }