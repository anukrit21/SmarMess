package com.demoApp.admin.service;

import com.demoApp.admin.dto.NotificationDTO;
import com.demoApp.admin.entity.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSender {

    public void sendNotification(NotificationDTO notification) {
        log.info("Sending notification: {}", notification);
        try {
            send(notification);
            log.info("Notification sent successfully: {}", notification);
        } catch (Exception e) {
            log.error("Failed to send notification: {}", notification, e);
        }
    }

    public void send(NotificationDTO notification) {
        log.info("Simulating sending notification to user ID {} with title '{}'",
                notification.getRecipientId(), notification.getTitle());
    }

    public void sendNotification(String recipientId, String title, String message, NotificationType type) {
        NotificationDTO notification = NotificationDTO.builder()
                .recipientId(recipientId)
                .title(title)
                .message(message)
                .type(type)
                .build();

        sendNotification(notification);
    }

    public void sendBulkNotifications(List<NotificationDTO> notifications) {
        log.info("Sending {} notifications", notifications.size());
        notifications.forEach(this::sendNotification);
    }
}
