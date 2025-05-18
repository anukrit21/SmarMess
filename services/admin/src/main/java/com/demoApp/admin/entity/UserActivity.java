package com.demoApp.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @Column(name = "description")
    private String description;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    @Column(name = "module")
    private String module;

    @Column(name = "action")
    private String action;

    @Column(name = "details")
    private String details;

    public enum ActivityType {
        LOGIN,
        LOGOUT,
        CREATE,
        UPDATE,
        DELETE,
        VIEW,
        EXPORT,
        IMPORT,
        DOWNLOAD,
        UPLOAD,
        OTHER
    }

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    // ✅ Explicit getters (add more if required by mapping logic)
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getModule() {
        return module;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
