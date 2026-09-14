package com.project.ms_idtech.repo;

import com.project.ms_idtech.model.Course;
import com.project.ms_idtech.model.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = {"teachers"})
    Page<Course> findAllByRecordStatusNot(RecordStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"teachers"})
    Optional<Course> findByIdAndRecordStatusNot(Long id, RecordStatus status);
}