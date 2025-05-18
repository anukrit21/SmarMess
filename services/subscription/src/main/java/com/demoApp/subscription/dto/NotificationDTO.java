package com.demoApp.subscription.dto;

import com.demoApp.subscription.entity.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long userId;
    private String title;
    private String message;
   private NotificationType type;
    private String status;
} 