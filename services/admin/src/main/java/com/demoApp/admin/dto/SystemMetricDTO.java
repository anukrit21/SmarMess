package com.demoApp.admin.dto;

import com.demoApp.admin.entity.SystemMetric;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemMetricDTO {
    private String metricName;
    private Double value;
    private String unit;
    private LocalDateTime timestamp;
    private String description;
    private String status;
    private String source;

    // Method to convert SystemMetric entity to SystemMetricDTO
    public static SystemMetricDTO fromEntity(SystemMetric entity) {
        return SystemMetricDTO.builder()
                .metricName(entity.getMetricName())
                .value(entity.getValue())
                .unit(entity.getUnit())
                .timestamp(entity.getTimestamp())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .source(entity.getSource())
                .build();
    }
}
