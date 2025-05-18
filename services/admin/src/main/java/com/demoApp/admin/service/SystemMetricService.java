package com.demoApp.admin.service;

import com.demoApp.admin.dto.SystemMetricDTO;
import com.demoApp.admin.entity.SystemMetric;
import com.demoApp.admin.repository.SystemMetricRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemMetricService {

    private final SystemMetricRepository systemMetricRepository;

    public List<SystemMetricDTO> getCurrentMetrics() {
        log.info("Collecting system metrics");
        List<SystemMetricDTO> metrics = new ArrayList<>();

        // CPU Usage
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        metrics.add(createMetric("cpu.usage", osBean.getSystemLoadAverage(), "%", "CPU Usage"));

        // Memory Usage
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long usedHeap = memoryBean.getHeapMemoryUsage().getUsed();
        long maxHeap = memoryBean.getHeapMemoryUsage().getMax();
        double memoryUsage = (double) usedHeap / maxHeap * 100;
        metrics.add(createMetric("memory.usage", memoryUsage, "%", "Memory Usage"));

        // Thread Count
        metrics.add(createMetric("thread.count",
                (double) ManagementFactory.getThreadMXBean().getThreadCount(),
                "count", "Active Threads"));

        // System Uptime
        metrics.add(createMetric("system.uptime",
                (double) ManagementFactory.getRuntimeMXBean().getUptime() / 1000,
                "seconds", "System Uptime"));

        // JVM Memory
        metrics.add(createMetric("jvm.memory.used",
                (double) memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024),
                "MB", "JVM Memory Used"));

        metrics.add(createMetric("jvm.memory.max",
                (double) memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024),
                "MB", "JVM Memory Max"));

        return metrics;
    }

    public SystemMetric getLatestMetricByServiceAndType(String serviceName, SystemMetric.MetricType metricType) {
        return systemMetricRepository.findLatestMetricByServiceAndType(serviceName, metricType);
    }

    public List<SystemMetricDTO> getAllMetrics() {
        return systemMetricRepository.findAll().stream()
                .map(SystemMetricDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Map<String, Double> getNumericValues() {
        return systemMetricRepository.findAll().stream()
                .collect(Collectors.toMap(
                        SystemMetric::getMetricName,
                        SystemMetric::getValue
                ));
    }

    private SystemMetricDTO createMetric(String name, Double value, String unit, String description) {
        return SystemMetricDTO.builder()
                .metricName(name)
                .value(value)
                .unit(unit)
                .timestamp(LocalDateTime.now())
                .description(description)
                .status(value > 80.0 ? "WARNING" : "NORMAL")
                .source("JVM")
                .build();
    }

    // --- Methods for controller compatibility ---
    public Page<SystemMetric> getAllSystemMetrics(Pageable pageable) {
        return systemMetricRepository.findAll(pageable);
    }

    public List<SystemMetricDTO> convertToDTOs(List<SystemMetric> metrics) {
        return metrics.stream().map(SystemMetricDTO::fromEntity).collect(Collectors.toList());
    }

    public SystemMetric getSystemMetricById(Long id) {
        return systemMetricRepository.findById(id).orElse(null);
    }

    public SystemMetricDTO convertToDTO(SystemMetric metric) {
        return SystemMetricDTO.fromEntity(metric);
    }

    public Page<SystemMetric> getSystemMetricsByService(String serviceName, Pageable pageable) {
        return systemMetricRepository.findByServiceName(serviceName, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "timestamp")));
    }

    public Page<SystemMetric> getSystemMetricsByType(SystemMetric.MetricType metricType, Pageable pageable) {
        return systemMetricRepository.findByMetricType(metricType, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "timestamp")));
    }

    public Page<SystemMetric> getSystemMetricsByServiceAndType(String serviceName, SystemMetric.MetricType metricType, Pageable pageable) {
        return systemMetricRepository.findByServiceNameAndMetricType(serviceName, metricType, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "timestamp")));
    }

    public Page<SystemMetric> getSystemMetricsByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return systemMetricRepository.findByTimestampBetween(start, end, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "timestamp")));
    }

    public List<SystemMetricDTO> getLatestMetricsForService(String serviceName) {
        List<SystemMetric> metrics = systemMetricRepository.findLatestMetricsForService(serviceName);
        return convertToDTOs(metrics);
    }

    public SystemMetric createSystemMetric(SystemMetricDTO dto) {
        SystemMetric metric = new SystemMetric();
        metric.setMetricName(dto.getMetricName());
        metric.setValue(dto.getValue());
        metric.setUnit(dto.getUnit());
        metric.setTimestamp(dto.getTimestamp());
        metric.setDescription(dto.getDescription());
        return systemMetricRepository.save(metric);
    }

    public SystemMetric recordMetric(String serviceName, SystemMetric.MetricType metricType, Double value, String unit, String description) {
        SystemMetric metric = new SystemMetric();
        metric.setServiceName(serviceName);
        metric.setMetricType(metricType);
        metric.setValue(value);
        metric.setUnit(unit);
        metric.setDescription(description);
        metric.setTimestamp(LocalDateTime.now());
        return systemMetricRepository.save(metric);
    }
}
