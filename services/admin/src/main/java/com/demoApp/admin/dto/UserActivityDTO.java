package com.demoApp.admin.dto;

import com.demoApp.admin.entity.UserActivity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityDTO {
    
    private Long id;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    private String username;
    
    @NotNull(message = "Activity type is required")
    private UserActivity.ActivityType activityType;
    
    private String description;
    
    private String ipAddress;
    
    private String userAgent;
    
    private String status;
    
    private LocalDateTime timestamp;
    
    private String module;
    
    private String action;
    
    private String details;
    
    private String sessionId;
    
    private String deviceInfo;
    
    private String location;

    // Lombok's @Data generates setters, but if required, you can add them manually.
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setActivityType(UserActivity.ActivityType activityType) {
        this.activityType = activityType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // fromEntity method remains the same
    public static UserActivityDTO fromEntity(UserActivity entity) {
        UserActivityDTO dto = new UserActivityDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setUsername(entity.getUsername());
        dto.setActivityType(entity.getActivityType());
        dto.setDescription(entity.getDescription());
        dto.setIpAddress(entity.getIpAddress());
        dto.setUserAgent(entity.getUserAgent());
        dto.setStatus(entity.getStatus());
        dto.setTimestamp(entity.getTimestamp());
        dto.setModule(entity.getModule());
        dto.setAction(entity.getAction());
        dto.setDetails(entity.getDetails());
        dto.setSessionId(entity.getSessionId());
        dto.setDeviceInfo(entity.getDeviceInfo());
        dto.setLocation(entity.getLocation());
        
        return dto;
    }
}
