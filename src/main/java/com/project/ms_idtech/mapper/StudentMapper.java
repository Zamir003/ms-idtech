package com.project.ms_idtech.mapper;

import com.project.ms_idtech.dto.student.StudentCreateRequest;
import com.project.ms_idtech.dto.student.StudentResponse;
import com.project.ms_idtech.model.Student;

public class StudentMapper {

    public static Student toEntity(StudentCreateRequest req) {
        Student s = new Student();
        s.setFullName(req.fullName());
        s.setEmail(req.email());
        s.setBirthDate(req.birthDate());
        return s;
    }

    public static StudentResponse toResponse(Student s) {
        return new StudentResponse(
                s.getId(),
                s.getFullName(),
                s.getEmail(),
                s.getBirthDate(),
                s.getStatus()
        );
    }
}