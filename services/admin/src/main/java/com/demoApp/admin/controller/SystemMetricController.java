package com.demoApp.admin.controller;

import com.demoApp.admin.dto.SystemMetricDTO;
import com.demoApp.admin.entity.SystemMetric;
import com.demoApp.admin.service.SystemMetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/system-metrics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SystemMetricController {
    
    private final SystemMetricService systemMetricService;

    @GetMapping
    public ResponseEntity<Page<SystemMetricDTO>> getAllSystemMetrics(Pageable pageable) {
        return ResponseEntity.ok(
            systemMetricService.getAllSystemMetrics(pageable)
                .map(systemMetricService::convertToDTO)
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SystemMetricDTO> getSystemMetricById(@PathVariable Long id) {
        return ResponseEntity.ok(
            systemMetricService.convertToDTO(
                systemMetricService.getSystemMetricById(id)
            )
        );
    }
    
    @GetMapping("/service/{serviceName}")
    public ResponseEntity<Page<SystemMetricDTO>> getSystemMetricsByService(
            @PathVariable String serviceName, 
            Pageable pageable) {
        return ResponseEntity.ok(
            systemMetricService.getSystemMetricsByService(serviceName, pageable)
                .map(systemMetricService::convertToDTO)
        );
    }
    
    @GetMapping("/type/{metricType}")
    public ResponseEntity<Page<SystemMetricDTO>> getSystemMetricsByType(
            @PathVariable SystemMetric.MetricType metricType, 
            Pageable pageable) {
        return ResponseEntity.ok(
            systemMetricService.getSystemMetricsByType(metricType, pageable)
                .map(systemMetricService::convertToDTO)
        );
    }
    
    @GetMapping("/service/{serviceName}/type/{metricType}")
    public ResponseEntity<Page<SystemMetricDTO>> getSystemMetricsByServiceAndType(
            @PathVariable String serviceName,
            @PathVariable SystemMetric.MetricType metricType,
            Pageable pageable) {
        return ResponseEntity.ok(
            systemMetricService.getSystemMetricsByServiceAndType(serviceName, metricType, pageable)
                .map(systemMetricService::convertToDTO)
        );
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<Page<SystemMetricDTO>> getSystemMetricsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            Pageable pageable) {
        return ResponseEntity.ok(
            systemMetricService.getSystemMetricsByDateRange(start, end, pageable)
                .map(systemMetricService::convertToDTO)
        );
    }
    
    @GetMapping("/latest/{serviceName}/{metricType}")
    public ResponseEntity<SystemMetricDTO> getLatestMetricByServiceAndType(
            @PathVariable String serviceName,
            @PathVariable SystemMetric.MetricType metricType) {
        return ResponseEntity.ok(
            systemMetricService.convertToDTO(
                systemMetricService.getLatestMetricByServiceAndType(serviceName, metricType)
            )
        );
    }
    
    @GetMapping("/latest/{serviceName}")
    public ResponseEntity<List<SystemMetricDTO>> getLatestMetricsForService(
            @PathVariable String serviceName) {
        return ResponseEntity.ok(
            systemMetricService.getLatestMetricsForService(serviceName)
        );
    }
    
    @PostMapping
    public ResponseEntity<SystemMetricDTO> createSystemMetric(
            @Valid @RequestBody SystemMetricDTO systemMetricDTO) {
        return new ResponseEntity<>(
            systemMetricService.convertToDTO(
                systemMetricService.createSystemMetric(systemMetricDTO)
            ),
            HttpStatus.CREATED
        );
    }
    
    @PostMapping("/record")
    public ResponseEntity<SystemMetricDTO> recordMetric(
            @RequestParam String serviceName,
            @RequestParam SystemMetric.MetricType metricType,
            @RequestParam(required = false) Double numericValue,
            @RequestParam(required = false) String stringValue,
            @RequestParam(required = false) String notes) {
        return ResponseEntity.ok(
            systemMetricService.convertToDTO(
                systemMetricService.recordMetric(
                    serviceName, metricType, numericValue, stringValue, notes)
            )
        );
    }
}