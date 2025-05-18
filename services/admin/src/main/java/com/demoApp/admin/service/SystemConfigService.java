package com.demoApp.admin.service;

import com.demoApp.admin.dto.SystemConfigDTO;
import com.demoApp.admin.entity.SystemConfig;
import com.demoApp.admin.repository.SystemConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfigService {
    private final SystemConfigRepository systemConfigRepository;

    @Transactional(readOnly = true)
    public List<SystemConfigDTO> getAllConfigs() {
        return systemConfigRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SystemConfigDTO getConfigByKey(String key) {
        return systemConfigRepository.findByKey(key)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Config not found"));
    }

    @Transactional
    public SystemConfigDTO updateConfig(String key, String value) {
        SystemConfig config = systemConfigRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Config not found"));
        config.setValue(value);
        return convertToDTO(systemConfigRepository.save(config));
    }

    private SystemConfigDTO convertToDTO(SystemConfig config) {
        return SystemConfigDTO.builder()
                .key(config.getKey())
                .value(config.getValue())
                .description(config.getDescription())
                .updatedAt(config.getUpdatedAt())
                .build();
    }
} 