package com.demoApp.admin.client;

import com.demoApp.admin.dto.PaymentAnalyticsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @GetMapping("/api/v1/payments/analytics")
    PaymentAnalyticsDTO getAnalytics(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate);
}
