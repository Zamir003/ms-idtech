package com.project.ms_idtech.dto.payment;

public record PaymentResponse(
        Long paymentId,
        String status
) { }