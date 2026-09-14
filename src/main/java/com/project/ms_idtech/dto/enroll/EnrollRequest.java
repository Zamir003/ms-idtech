package com.project.ms_idtech.dto.enroll;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EnrollRequest(
        @NotNull Long courseId,
        @NotNull Long customerId,
        @NotNull BigDecimal amount,
        @NotBlank String currency,
        @NotBlank String studentFullName,
        @Email @NotBlank String studentEmail,
        LocalDate studentBirthDate
) { }