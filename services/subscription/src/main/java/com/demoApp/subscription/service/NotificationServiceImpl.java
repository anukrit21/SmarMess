package com.demoApp.subscription.service;

import com.demoApp.subscription.client.NotificationRequest;
import com.demoApp.subscription.client.NotificationService;
import com.demoApp.subscription.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(NotificationRequest request) {
        log.info("Sending notification to user ID {}: [{}] {}", request.getUserId(), request.getTitle(), request.getMessage());
        // Simulate sending a notification
    }
}