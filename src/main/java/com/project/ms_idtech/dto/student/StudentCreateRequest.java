package com.project.ms_idtech.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record StudentCreateRequest(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        LocalDate birthDate
) { }