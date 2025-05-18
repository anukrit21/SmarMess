package com.demoApp.admin.service;

import com.demoApp.admin.dto.ServiceStatusDTO;
import com.demoApp.admin.entity.ServiceStatus;
import com.demoApp.admin.exception.ResourceNotFoundException;
import com.demoApp.admin.repository.ServiceStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceStatusService {
    
    private final ServiceStatusRepository serviceStatusRepository;
    private final ModelMapper modelMapper;
    private final WebClient.Builder webClientBuilder;
    
    public List<ServiceStatus> getAllServiceStatuses() {
        return serviceStatusRepository.findAll();
    }
    
    public List<ServiceStatus> getAllServiceStatus() {
        return serviceStatusRepository.findAll();
    }
    
    public Optional<ServiceStatus> getServiceStatusById(Long id) {
        return serviceStatusRepository.findById(id);
    }
    
    public Optional<ServiceStatus> getServiceStatusByName(String serviceName) {
        return serviceStatusRepository.findByServiceName(serviceName);
    }
    
    @Transactional
    public ServiceStatus createServiceStatus(ServiceStatusDTO serviceStatusDTO) {
        ServiceStatus serviceStatus = modelMapper.map(serviceStatusDTO, ServiceStatus.class);
        serviceStatus.setStatus(ServiceStatus.Status.valueOf(serviceStatusDTO.getStatus()));
        serviceStatus.setResponseTime(serviceStatusDTO.getResponseTime());
        serviceStatus.setErrorCount(serviceStatusDTO.getErrorCount());
        serviceStatus.setLastChecked(LocalDateTime.now());
        return serviceStatusRepository.save(serviceStatus);
    }
    
    @Transactional
    public ServiceStatus updateServiceStatus(Long id, ServiceStatusDTO serviceStatusDTO) {
        ServiceStatus serviceStatus = serviceStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service status not found"));

        if (serviceStatusDTO.getServiceName() != null) {
            serviceStatus.setServiceName(serviceStatusDTO.getServiceName());
        }
        if (serviceStatusDTO.getServiceUrl() != null) {
            serviceStatus.setServiceUrl(serviceStatusDTO.getServiceUrl());
        }
        if (serviceStatusDTO.getVersion() != null) {
            serviceStatus.setVersion(serviceStatusDTO.getVersion());
        }
        if (serviceStatusDTO.getHealthDetails() != null) {
            serviceStatus.setHealthDetails(serviceStatusDTO.getHealthDetails());
        }
        if (serviceStatusDTO.getStatus() != null) {
            serviceStatus.setStatus(ServiceStatus.Status.valueOf(serviceStatusDTO.getStatus()));
        }
        if (serviceStatusDTO.getResponseTime() != null) {
            serviceStatus.setResponseTime(serviceStatusDTO.getResponseTime());
        }
        if (serviceStatusDTO.getErrorCount() != null) {
            serviceStatus.setErrorCount(serviceStatusDTO.getErrorCount());
        }
        serviceStatus.setLastChecked(LocalDateTime.now());
        return serviceStatusRepository.save(serviceStatus);
    }
    
    @Transactional
    public void deleteServiceStatus(Long id) {
        serviceStatusRepository.deleteById(id);
    }
    
    @Scheduled(fixedRate = 60000) // Check every minute
    @Transactional
    public void checkAllServicesHealth() {
        List<ServiceStatus> services = serviceStatusRepository.findAll();
        
        for (ServiceStatus service : services) {
            try {
                // Try to call the service's health endpoint
                String healthEndpoint = service.getServiceUrl() + "/actuator/health";
                
                webClientBuilder.build()
                    .get()
                    .uri(healthEndpoint)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                        response -> {
                            service.setStatus(ServiceStatus.Status.UP);
                            service.setHealthDetails(response);
                            service.setLastChecked(LocalDateTime.now());
                            serviceStatusRepository.save(service);
                        },
                        error -> {
                            service.setStatus(ServiceStatus.Status.DOWN);
                            service.setHealthDetails("Error: " + error.getMessage());
                            service.setLastChecked(LocalDateTime.now());
                            serviceStatusRepository.save(service);
                        }
                    );
                
            } catch (Exception e) {
                service.setStatus(ServiceStatus.Status.DOWN);
                service.setHealthDetails("Error: " + e.getMessage());
                service.setLastChecked(LocalDateTime.now());
                serviceStatusRepository.save(service);
            }
        }
    }
    
    public ServiceStatusDTO convertToDTO(ServiceStatus serviceStatus) {
        return ServiceStatusDTO.builder()
                .id(serviceStatus.getId())
                .serviceName(serviceStatus.getServiceName())
                .status(serviceStatus.getStatus().name())
                .lastChecked(serviceStatus.getLastChecked())
                .responseTime(serviceStatus.getResponseTime())
                .errorCount(serviceStatus.getErrorCount())
                .build();
    }
    
    public List<ServiceStatusDTO> convertToDTOs(List<ServiceStatus> serviceStatuses) {
        return serviceStatuses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ServiceStatus updateServiceHealth(String serviceName, String healthDetails, ServiceStatus.Status status) {
        ServiceStatus service = serviceStatusRepository.findByServiceName(serviceName)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        service.setHealthDetails(healthDetails);
        service.setStatus(status);
        service.setLastChecked(LocalDateTime.now());
        return serviceStatusRepository.save(service);
    }

    @Transactional
    public ServiceStatus updateServiceError(String serviceName, String errorDetails) {
        ServiceStatus service = serviceStatusRepository.findByServiceName(serviceName)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        service.setHealthDetails(errorDetails);
        service.setStatus(ServiceStatus.Status.DOWN);
        service.setErrorCount(service.getErrorCount() + 1);
        service.setLastChecked(LocalDateTime.now());
        return serviceStatusRepository.save(service);
    }

    @Transactional
    public ServiceStatus updateServiceStatus(String serviceName, ServiceStatus.Status status) {
        ServiceStatus service = serviceStatusRepository.findByServiceName(serviceName)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        service.setStatus(status);
        service.setLastChecked(LocalDateTime.now());
        return serviceStatusRepository.save(service);
    }
} 