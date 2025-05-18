package com.demoApp.campus_module.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCreateDTO {
    @NotBlank(message = "Event name is required")
    private String name;

    private String description;

    @NotBlank(message = "Event type is required")
    private String type;

    @NotNull(message = "Location ID is required")
    private Long locationId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
} 