package com.demoApp.campus_module.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CampusEventDTO {
    private Long id;
    private Long campusId;
    private Long locationId;
    private String name;
    private String description;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
} 