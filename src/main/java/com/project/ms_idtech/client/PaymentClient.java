package com.project.ms_idtech.client;

import com.project.ms_idtech.dto.payment.PaymentCreateRequest;
import com.project.ms_idtech.dto.payment.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(name = "ms-payment", url = "${clients.payment.base-url}")
public interface PaymentClient {

    @PostMapping("/api/v1/payments/charge")
    PaymentResponse charge(@Valid @RequestBody PaymentCreateRequest request);
}