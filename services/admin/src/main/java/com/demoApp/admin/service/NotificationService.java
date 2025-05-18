package com.demoApp.admin.service;

import com.demoApp.admin.dto.NotificationDTO;
import com.demoApp.admin.entity.Notification;
import com.demoApp.admin.entity.NotificationType;
import com.demoApp.admin.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    
    // These services would be injected if available
    // private final UserService userService; // For fetching user details
    // private final EmailService emailService; // For sending email notifications
    // private final WebSocketService webSocketService; // For real-time in-app notifications

    @Transactional
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        log.info("Creating new notification for recipient: {}", notificationDTO.getRecipientId());
        Notification notification = convertToEntity(notificationDTO);
        notification = notificationRepository.save(notification);
        return convertToDTO(notification);
    }

    @Transactional
    public NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO) {
        log.info("Updating notification: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        updateEntityFromDTO(notification, notificationDTO);
        notification = notificationRepository.save(notification);
        return convertToDTO(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        log.info("Deleting notification: {}", id);
        notificationRepository.deleteById(id);
    }

    public Page<NotificationDTO> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable).map(this::convertToDTO);
    }

    public List<NotificationDTO> getUnreadNotifications(String recipientId) {
        return notificationRepository.findByRecipientIdAndIsReadFalseAndIsActiveTrue(recipientId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markAsRead(Long id) {
        log.info("Marking notification as read: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead(String recipientId) {
        log.info("Marking all notifications as read for recipient: {}", recipientId);
        List<Notification> notifications = notificationRepository.findByRecipientIdAndIsReadFalseAndIsActiveTrue(recipientId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    /**
     * Sends a notification to the specified recipient through appropriate channels
     * based on the notification type and user preferences.
     *
     * @param recipientId the ID of the recipient
     * @param title the notification title
     * @param message the notification message
     * @param type the type of notification
     */
    public void sendNotification(String recipientId, String title, String message, NotificationType type) {
        log.info("Preparing to send notification to {}: {} - {} [{}]", recipientId, title, message, type);
        
        try {
            // Create and save the notification
            Notification notification = Notification.builder()
                    .recipientId(recipientId)
                    .title(title)
                    .message(message)
                    .type(type)
                    .isRead(false)
                    .isActive(true)
                    .build();
            
            notification = notificationRepository.save(notification);
            
            // Get user notification preferences (simplified - in real app, fetch from user settings)
            boolean sendEmail = true; // Default to true, should come from user preferences
            boolean sendInApp = true; // Default to true
            
            // Send notification through appropriate channels based on type and preferences
            switch (type) {
                case SYSTEM_ALERT:
                case ACCOUNT_DELETED:
                case PASSWORD_RESET:
                    // For critical alerts, always send both in-app and email
                    if (sendInApp) {
                        sendInAppNotification(notification);
                    }
                    if (sendEmail) {
                        sendEmailNotification(recipientId, title, message);
                    }
                    break;
                    
                case PROMOTION:
                    // For promotional content, respect user preferences strictly
                    if (sendEmail) {
                        sendEmailNotification(recipientId, title, message);
                    }
                    if (sendInApp) {
                        sendInAppNotification(notification);
                    }
                    break;
                    
                case ORDER_UPDATE:
                case PAYMENT_UPDATE:
                case DELIVERY_UPDATE:
                case SUBSCRIPTION_UPDATE:
                    // For transactional updates, prefer in-app but can send email if enabled
                    if (sendInApp) {
                        sendInAppNotification(notification);
                    }
                    if (sendEmail) {
                        sendEmailNotification(recipientId, title, message);
                    }
                    break;
                    
                default:
                    // For all other types (USER_ALERT, CUSTOM, WELCOME, STATUS_UPDATE)
                    // Default to in-app only
                    if (sendInApp) {
                        sendInAppNotification(notification);
                    }
            }
            
            log.info("Successfully sent notification with ID: {}", notification.getId());
            
        } catch (Exception e) {
            log.error("Failed to send notification to {}: {}", recipientId, e.getMessage(), e);
            throw new RuntimeException("Failed to send notification: " + e.getMessage(), e);
        }
    }
    
    /**
     * Sends an email notification to the user.
     */
    private void sendEmailNotification(String recipientId, String subject, String content) {
        try {
            // In a real implementation, this would use the email service to send an email
            // For now, we'll just log it
            log.info("Sending email to {} - Subject: {}, Content: {}", recipientId, subject, content);
            // emailService.sendEmail(recipientId, subject, content);
        } catch (Exception e) {
            log.error("Failed to send email notification to {}: {}", recipientId, e.getMessage(), e);
            // We don't rethrow here to allow other notification methods to be attempted
        }
    }
    
    /**
     * Sends a real-time in-app notification via WebSocket.
     */
    private void sendInAppNotification(Notification notification) {
        try {
            // In a real implementation, this would use WebSocket to push the notification
            // For now, we'll just log it
            log.info("Sending in-app notification to user {}: {}", 
                    notification.getRecipientId(), notification.getTitle());
            // webSocketService.sendNotification(notification.getRecipientId(), convertToDTO(notification));
        } catch (Exception e) {
            log.error("Failed to send in-app notification: {}", e.getMessage(), e);
            // We don't rethrow here to allow other notification methods to be attempted
        }
    }

    private Notification convertToEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setType(dto.getType());
        notification.setMessage(dto.getMessage());
        notification.setRecipientId(dto.getRecipientId());
        notification.setTitle(dto.getTitle());
        notification.setRead(dto.getRead());
        notification.setCreatedAt(LocalDateTime.now());
        return notification;
    }

    private NotificationDTO convertToDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .type(notification.getType())
                .message(notification.getMessage())
                .recipientId(notification.getRecipientId())
                .title(notification.getTitle())
                .read(notification.isRead())
                .timestamp(notification.getCreatedAt())
                .build();
    }

    private void updateEntityFromDTO(Notification notification, NotificationDTO dto) {
        notification.setType(dto.getType());
        notification.setMessage(dto.getMessage());
        notification.setRecipientId(dto.getRecipientId());
        notification.setTitle(dto.getTitle());
        notification.setRead(dto.getRead());
        notification.setUpdatedAt(LocalDateTime.now());
    }
} 