package com.demoApp.admin.client;

import com.demoApp.admin.dto.DeliveryAnalyticsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "delivery-service")
public interface DeliveryServiceClient {

    @GetMapping("/api/v1/delivery/analytics")
    DeliveryAnalyticsDTO getAnalytics(
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    );
}
