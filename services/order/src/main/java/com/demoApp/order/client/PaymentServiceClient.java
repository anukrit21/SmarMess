package com.demoApp.order.client;

import com.demoApp.order.dto.PaymentRequestDTO;
import com.demoApp.order.dto.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("/api/payments/process")
    PaymentResponseDTO processPayment(@RequestBody PaymentRequestDTO request);
} 