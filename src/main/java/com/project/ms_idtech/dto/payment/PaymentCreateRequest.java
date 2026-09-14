package com.project.ms_idtech.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentCreateRequest(
        @NotNull Long enrollmentId,
        @NotNull Long customerId,
        @NotNull BigDecimal amount,
        @NotBlank String currency,
        @NotBlank String payerEmail
) { }