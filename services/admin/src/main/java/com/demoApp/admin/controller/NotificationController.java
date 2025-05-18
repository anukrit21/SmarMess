package com.demoApp.admin.controller;

import com.demoApp.admin.dto.ApiResponse;
import com.demoApp.admin.dto.NotificationDTO;
import com.demoApp.admin.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Endpoint to create a new notification
    @PostMapping
    public ResponseEntity<ApiResponse<NotificationDTO>> createNotification(
            @RequestBody NotificationDTO notificationDTO) {
        log.info("Received request to create notification for recipient: {}", notificationDTO.getRecipientId());
        return ResponseEntity.ok(ApiResponse.success(notificationService.createNotification(notificationDTO)));
    }

    // Endpoint to update a notification
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationDTO>> updateNotification(
            @PathVariable Long id,
            @RequestBody NotificationDTO notificationDTO) {
        log.info("Received request to update notification: {}", id);
        return ResponseEntity.ok(ApiResponse.success(notificationService.updateNotification(id, notificationDTO)));
    }

    // Endpoint to delete a notification
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteNotification(@PathVariable Long id) {
        log.info("Received request to delete notification: {}", id);
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted successfully"));
    }

    // Endpoint to get all notifications with pagination
    @GetMapping
    public ResponseEntity<ApiResponse<Page<NotificationDTO>>> getAllNotifications(Pageable pageable) {
        log.info("Received request to get all notifications");
        return ResponseEntity.ok(ApiResponse.success(notificationService.getAllNotifications(pageable)));
    }

    // Endpoint to get unread notifications for a recipient
    @GetMapping("/unread/{recipient}")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getUnreadNotifications(
            @PathVariable String recipientId) {
        log.info("Received request to get unread notifications for recipient: {}", recipientId);
        return ResponseEntity.ok(ApiResponse.success(notificationService.getUnreadNotifications(recipientId)));
    }

    // Endpoint to mark a notification as read
    @PostMapping("/{id}/read")
    public ResponseEntity<ApiResponse<String>> markAsRead(@PathVariable Long id) {
        log.info("Received request to mark notification as read: {}", id);
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read"));
    }

    // Endpoint to mark all notifications as read for a recipient
    @PostMapping("/read-all/{recipient}")
    public ResponseEntity<ApiResponse<String>> markAllAsRead(@PathVariable String recipientId) {
        log.info("Received request to mark all notifications as read for recipient: {}", recipientId);
        notificationService.markAllAsRead(recipientId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read"));
    }
}