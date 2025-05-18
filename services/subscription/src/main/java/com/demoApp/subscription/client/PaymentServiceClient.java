package com.demoApp.subscription.client;

import com.demoApp.subscription.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service", url = "${payment.service.url}")
public interface PaymentServiceClient {

    @PostMapping("/api/payments")
    void processPayment(@RequestBody PaymentRequest paymentRequest);

    @GetMapping("/api/payments/{paymentId}")
    String getPaymentStatus(@PathVariable("paymentId") String paymentId);

    @PostMapping("/api/payments/{paymentId}/refund")
    void refundPayment(@PathVariable("paymentId") String paymentId);
}
