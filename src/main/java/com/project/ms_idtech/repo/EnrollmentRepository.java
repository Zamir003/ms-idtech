package com.project.ms_idtech.repo;

import com.project.ms_idtech.model.Enrollment;
import com.project.ms_idtech.model.RecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @EntityGraph(attributePaths = {"student", "course"})
    Page<Enrollment> findAllByRecordStatusNot(RecordStatus status, Pageable pageable);
}