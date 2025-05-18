package com.demoApp.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_status")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceStatus {

    public enum Status {
        UP, DOWN, DEGRADED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "service_url")
    private String serviceUrl;

    @Column(name = "version")
    private String version;

    @Column(name = "health_details")
    private String healthDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "last_checked", nullable = false)
    private LocalDateTime lastChecked;

    @Column(name = "response_time")
    private Long responseTime;

    @Column(name = "error_count")
    private Integer errorCount;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
