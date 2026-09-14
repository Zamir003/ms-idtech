package com.project.ms_idtech.controller;

import com.project.ms_idtech.dto.PageResponse;
import com.project.ms_idtech.dto.enroll.EnrollRequest;
import com.project.ms_idtech.dto.enroll.EnrollmentResponse;
import com.project.ms_idtech.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enroll(@Valid @RequestBody EnrollRequest req) {
        return enrollmentService.enroll(req);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<EnrollmentResponse> list(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        return enrollmentService.list(page, size);
    }
}