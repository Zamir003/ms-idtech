package com.project.ms_idtech.service;

import com.project.ms_idtech.dto.PageResponse;
import com.project.ms_idtech.dto.course.CourseCreateRequest;
import com.project.ms_idtech.dto.course.CoursePatchRequest;
import com.project.ms_idtech.dto.course.CourseResponse;
import com.project.ms_idtech.exception.custom.NotFoundException;
import com.project.ms_idtech.mapper.CourseMapper;
import com.project.ms_idtech.model.Course;
import com.project.ms_idtech.model.RecordStatus;
import com.project.ms_idtech.repo.CourseRepository;
import com.project.ms_idtech.repo.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    public CourseResponse create(CourseCreateRequest req) {
        Course c = new Course();
        c.setTitle(req.title());
        c.setDescription(req.description());

        if (req.teacherIds() != null && !req.teacherIds().isEmpty()) {
            c.setTeachers(new HashSet<>(teacherRepository.findAllById(req.teacherIds())));
        }

        return CourseMapper.toResponse(courseRepository.save(c));
    }

    @Transactional(readOnly = true)
    public PageResponse<CourseResponse> list(int page, int size) {
        var p = courseRepository.findAllByRecordStatusNot(RecordStatus.DELETED, PageRequest.of(page, size));
        List<CourseResponse> items = p.getContent().stream().map(CourseMapper::toResponse).toList();
        return new PageResponse<>(items, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Transactional(readOnly = true)
    public CourseResponse get(Long id) {
        Course c = courseRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));
        return CourseMapper.toResponse(c);
    }

    @Transactional
    public CourseResponse patch(Long id, CoursePatchRequest req) {
        Course c = courseRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));

        if (req.title() != null) c.setTitle(req.title());
        if (req.description() != null) c.setDescription(req.description());
        if (req.courseStatus() != null) c.setCourseStatus(req.courseStatus());
        if (req.recordStatus() != null) c.setRecordStatus(req.recordStatus());

        if (req.teacherIds() != null) {
            c.setTeachers(new HashSet<>(teacherRepository.findAllById(req.teacherIds())));
        }

        return CourseMapper.toResponse(courseRepository.save(c));
    }

    @Transactional
    public void delete(Long id) {
        Course c = courseRepository.findByIdAndRecordStatusNot(id, RecordStatus.DELETED)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));
        courseRepository.delete(c);
    }
}