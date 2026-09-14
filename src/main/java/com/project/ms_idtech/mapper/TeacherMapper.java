package com.project.ms_idtech.mapper;

import com.project.ms_idtech.dto.teacher.TeacherCreateRequest;
import com.project.ms_idtech.dto.teacher.TeacherResponse;
import com.project.ms_idtech.model.Teacher;

public class TeacherMapper {

    public static Teacher toEntity(TeacherCreateRequest req) {
        Teacher t = new Teacher();
        t.setFirstName(req.firstName());
        t.setLastName(req.lastName());
        t.setEmail(req.email());
        t.setBirthDate(req.birthDate());
        return t;
    }

    public static TeacherResponse toResponse(Teacher t) {
        return new TeacherResponse(
                t.getId(),
                t.getFirstName(),
                t.getLastName(),
                t.getEmail(),
                t.getBirthDate(),
                t.getStatus()
        );
    }
}