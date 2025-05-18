package com.demoApp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceStatusDTO {
    private Long id;
    private String serviceName;
    private String serviceUrl;
    private String version;
    private String healthDetails;
    private String status;
    private Long responseTime;
    private Integer errorCount;
    private LocalDateTime lastChecked;
}
