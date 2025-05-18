package com.demoApp.campus_module.client;

import com.demoApp.campus_module.entity.NotificationType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service")
public interface NotificationService {
    @PostMapping("/api/v1/notifications")
    void sendNotification(
        @RequestParam Long campusId,
        @RequestParam String title,
        @RequestParam String message,
        @RequestParam NotificationType type
    );
} 