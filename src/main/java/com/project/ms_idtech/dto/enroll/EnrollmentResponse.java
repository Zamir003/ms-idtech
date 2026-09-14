package com.project.ms_idtech.dto.enroll;

import com.project.ms_idtech.model.EnrollmentStatus;

public record EnrollmentResponse(
        Long id,
        Long studentId,
        Long courseId,
        Long paymentId,
        EnrollmentStatus enrollmentStatus
) { }