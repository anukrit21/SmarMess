package com.demoApp.admin.dto;

import com.demoApp.admin.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String recipientId;
    private String title;
    private String message;
    private NotificationType type;
    private String priority;
    private LocalDateTime timestamp;
    private Boolean read;
} 