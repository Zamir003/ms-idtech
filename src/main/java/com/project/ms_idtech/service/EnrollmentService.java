package com.project.ms_idtech.service;

import com.project.ms_idtech.dto.PageResponse;
import com.project.ms_idtech.dto.enroll.EnrollRequest;
import com.project.ms_idtech.dto.enroll.EnrollmentResponse;
import com.project.ms_idtech.dto.payment.PaymentCreateRequest;
import com.project.ms_idtech.exception.custom.BadRequestException;
import com.project.ms_idtech.exception.custom.NotFoundException;
import com.project.ms_idtech.model.*;
import com.project.ms_idtech.repo.CourseRepository;
import com.project.ms_idtech.repo.EnrollmentRepository;
import com.project.ms_idtech.repo.StudentRepository;
import com.project.ms_idtech.client.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final PaymentClient paymentClient;

    public EnrollmentResponse enroll(EnrollRequest req) {
        Enrollment pending = createPending(req); // TX #1
        var payment = paymentClient.charge(new PaymentCreateRequest(
                pending.getId(),
                req.customerId(),
                req.amount(),
                req.currency(),
                req.studentEmail()
        ));

        // TX #2
        return finalizeEnrollment(pending.getId(), payment.paymentId(), payment.status());
    }

    @Transactional
    protected Enrollment createPending(EnrollRequest req) {
        Course course = courseRepository.findByIdAndRecordStatusNot(req.courseId(), RecordStatus.DELETED)
                .orElseThrow(() -> new NotFoundException("Course not found: " + req.courseId()));

        Student student = studentRepository.findByEmail(req.studentEmail())
                .orElseGet(() -> {
                    Student s = new Student();
                    s.setFullName(req.studentFullName());
                    s.setEmail(req.studentEmail());
                    s.setBirthDate(req.studentBirthDate());
                    return studentRepository.save(s);
                });

        Enrollment e = new Enrollment();
        e.setCourse(course);
        e.setStudent(student);
        e.setEnrollmentStatus(EnrollmentStatus.PENDING_PAYMENT);

        return enrollmentRepository.save(e);
    }

    @Transactional
    protected EnrollmentResponse finalizeEnrollment(Long enrollmentId, Long paymentId, String paymentStatus) {
        Enrollment e = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NotFoundException("Enrollment not found: " + enrollmentId));

        e.setPaymentId(paymentId);

        if ("PAID".equalsIgnoreCase(paymentStatus)) {
            e.setEnrollmentStatus(EnrollmentStatus.ENROLLED);
        } else if ("FAILED".equalsIgnoreCase(paymentStatus)) {
            e.setEnrollmentStatus(EnrollmentStatus.PAYMENT_FAILED);
        } else {
            throw new BadRequestException("Unknown payment status: " + paymentStatus);
        }

        Enrollment saved = enrollmentRepository.save(e);
        return new EnrollmentResponse(saved.getId(), saved.getStudent().getId(), saved.getCourse().getId(),
                saved.getPaymentId(), saved.getEnrollmentStatus());
    }

    @Transactional(readOnly = true)
    public PageResponse<EnrollmentResponse> list(int page, int size) {
        var p = enrollmentRepository.findAllByRecordStatusNot(RecordStatus.DELETED, PageRequest.of(page, size));
        List<EnrollmentResponse> items = p.getContent().stream()
                .map(e -> new EnrollmentResponse(e.getId(), e.getStudent().getId(), e.getCourse().getId(),
                        e.getPaymentId(), e.getEnrollmentStatus()))
                .toList();
        return new PageResponse<>(items, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }
}