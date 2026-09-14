package com.project.ms_idtech.service;

import com.project.ms_idtech.dto.PageResponse;
import com.project.ms_idtech.dto.teacher.TeacherCreateRequest;
import com.project.ms_idtech.dto.teacher.TeacherPatchRequest;
import com.project.ms_idtech.dto.teacher.TeacherResponse;
import com.project.ms_idtech.exception.custom.NotFoundException;
import com.project.ms_idtech.mapper.TeacherMapper;
import com.project.ms_idtech.model.Teacher;
import com.project.ms_idtech.repo.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Transactional
    public TeacherResponse create(TeacherCreateRequest req) {
        Teacher t = TeacherMapper.toEntity(req);
        return TeacherMapper.toResponse(teacherRepository.save(t));
    }

    @Transactional(readOnly = true)
    public PageResponse<TeacherResponse> list(int page, int size) {
        var p = teacherRepository.findAll(PageRequest.of(page, size));
        List<TeacherResponse> items = p.getContent().stream().map(TeacherMapper::toResponse).toList();
        return new PageResponse<>(items, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Transactional(readOnly = true)
    public TeacherResponse get(Long id) {
        return teacherRepository.findById(id)
                .map(TeacherMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + id));
    }

    @Transactional
    public TeacherResponse patch(Long id, TeacherPatchRequest req) {
        Teacher t = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + id));

        if (req.firstName() != null) t.setFirstName(req.firstName());
        if (req.lastName() != null) t.setLastName(req.lastName());
        if (req.birthDate() != null) t.setBirthDate(req.birthDate());
        if (req.status() != null) t.setStatus(req.status());

        return TeacherMapper.toResponse(teacherRepository.save(t));
    }

    @Transactional
    public void delete(Long id) {
        Teacher t = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + id));
        teacherRepository.delete(t);
    }
}