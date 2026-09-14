package com.project.ms_idtech.service;

import com.project.ms_idtech.dto.PageResponse;
import com.project.ms_idtech.dto.student.StudentCreateRequest;
import com.project.ms_idtech.dto.student.StudentResponse;
import com.project.ms_idtech.exception.custom.NotFoundException;
import com.project.ms_idtech.mapper.StudentMapper;
import com.project.ms_idtech.model.Student;
import com.project.ms_idtech.repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public StudentResponse create(StudentCreateRequest req) {
        Student s = StudentMapper.toEntity(req);
        return StudentMapper.toResponse(studentRepository.save(s));
    }

    @Transactional(readOnly = true)
    public PageResponse<StudentResponse> list(int page, int size) {
        var p = studentRepository.findAll(PageRequest.of(page, size));
        List<StudentResponse> items = p.getContent().stream().map(StudentMapper::toResponse).toList();
        return new PageResponse<>(items, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Transactional(readOnly = true)
    public StudentResponse get(Long id) {
        return studentRepository.findById(id)
                .map(StudentMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));
    }
}