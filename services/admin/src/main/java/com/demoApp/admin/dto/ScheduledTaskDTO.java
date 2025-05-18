package com.demoApp.admin.dto;

import com.demoApp.admin.entity.ScheduledTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTaskDTO {
    private Long id;
    private String serviceName;
    private String taskName;
    private String cronExpression;
    private ScheduledTask.TaskStatus status;
    private LocalDateTime nextExecutionTime;
    private LocalDateTime lastExecutionTime;
    private String lastExecutionResult;
    private boolean enabled;
    private String description;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
} 