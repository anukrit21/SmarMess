package com.demoApp.subscription.client;

import com.demoApp.subscription.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "notification-service", url = "${notification.service.url}")
public interface NotificationServiceClient {
    @PostMapping("/api/notifications")
    void sendNotification(@RequestBody NotificationDTO notificationDTO);

    @PostMapping("/api/notifications/bulk")
    void sendBulkNotifications(@RequestBody List<NotificationDTO> notificationDTOs);
} 