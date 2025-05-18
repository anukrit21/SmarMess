package com.demoApp.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_metrics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metric_name", nullable = false)
    private String metricName;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "unit")
    private String unit;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "description")
    private String description;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Enumerated(EnumType.STRING)
    @Column(name = "metric_type", nullable = false)
    private MetricType metricType;

    @Column(name = "string_value")
    private String stringValue;

    @Column
    private String notes;

    public enum MetricType {
        CPU_USAGE,
        MEMORY_USAGE,
        DISK_USAGE,
        NETWORK_TRAFFIC,
        REQUEST_COUNT,
        ERROR_COUNT,
        RESPONSE_TIME,
        CUSTOM
    }

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    public String getMetricName() {
        return metricName;
    }

    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getCategory() {
        if (metricType != null) {
            switch (metricType) {
                case CPU_USAGE:
                case MEMORY_USAGE:
                case DISK_USAGE:
                    return "Resource Usage";
                case NETWORK_TRAFFIC:
                    return "Network";
                case REQUEST_COUNT:
                case ERROR_COUNT:
                    return "Request Metrics";
                case RESPONSE_TIME:
                    return "Performance";
                default:
                    return "Unknown";
            }
        }
        return "Unknown";
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getThreshold() {
        // Default threshold values based on metric type
        switch (metricType) {
            case CPU_USAGE:
                return "80%";
            case MEMORY_USAGE:
                return "85%";
            case DISK_USAGE:
                return "90%";
            case NETWORK_TRAFFIC:
                return "1000 Mbps";
            case REQUEST_COUNT:
                return "1000/min";
            case ERROR_COUNT:
                return "10/min";
            case RESPONSE_TIME:
                return "500ms";
            default:
                return "N/A";
        }
    }

    public String getStatus() {
        if (value == null) return "UNKNOWN";
        
        switch (metricType) {
            case CPU_USAGE:
            case MEMORY_USAGE:
            case DISK_USAGE:
                return value > 90 ? "CRITICAL" : 
                       value > 80 ? "WARNING" : "NORMAL";
            case NETWORK_TRAFFIC:
                return value > 1000 ? "CRITICAL" : 
                       value > 800 ? "WARNING" : "NORMAL";
            case REQUEST_COUNT:
                return value > 1000 ? "CRITICAL" : 
                       value > 800 ? "WARNING" : "NORMAL";
            case ERROR_COUNT:
                return value > 10 ? "CRITICAL" : 
                       value > 5 ? "WARNING" : "NORMAL";
            case RESPONSE_TIME:
                return value > 500 ? "CRITICAL" : 
                       value > 300 ? "WARNING" : "NORMAL";
            default:
                return "NORMAL";
        }
    }

    public String getSource() {
        return serviceName != null ? serviceName : "UNKNOWN";
    }

    public String getTags() {
        return String.format("service=%s,type=%s", 
            serviceName != null ? serviceName : "unknown",
            metricType != null ? metricType.name() : "unknown");
    }

    public Double getMinValue() {
        return value != null ? value * 0.8 : null;
    }

    public Double getMaxValue() {
        return value != null ? value * 1.2 : null;
    }

    public Double getAvgValue() {
        return value;
    }

    public Integer getSampleCount() {
        return 1; // Default to 1 for single measurement
    }

    public void setNumericValue(Double value) {
        this.value = value;
    }
}
