package com.project.ms_idtech.mapper;

import com.project.ms_idtech.dto.course.CourseResponse;
import com.project.ms_idtech.model.Course;

import java.util.Set;
import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseResponse toResponse(Course c) {
        Set<CourseResponse.TeacherMini> teachers = c.getTeachers().stream()
                .map(t -> new CourseResponse.TeacherMini(t.getId(), t.getFirstName(), t.getLastName(), t.getEmail()))
                .collect(Collectors.toSet());

        return new CourseResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getCourseStatus(),
                c.getRecordStatus(),
                teachers
        );
    }
}