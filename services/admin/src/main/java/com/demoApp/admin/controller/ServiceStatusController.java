package com.demoApp.admin.controller;

import com.demoApp.admin.dto.ApiResponse;
import com.demoApp.admin.dto.ServiceStatusDTO;
import com.demoApp.admin.entity.ServiceStatus;
import com.demoApp.admin.exception.ResourceNotFoundException;
import com.demoApp.admin.service.ServiceStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-status")
@RequiredArgsConstructor
public class ServiceStatusController {
    
    private final ServiceStatusService serviceStatusService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ServiceStatusDTO>> getAllServiceStatuses() {
        List<ServiceStatus> serviceStatuses = serviceStatusService.getAllServiceStatuses();
        return ResponseEntity.ok(serviceStatusService.convertToDTOs(serviceStatuses));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceStatusDTO> getServiceStatusById(@PathVariable Long id) {
        ServiceStatus serviceStatus = serviceStatusService.getServiceStatusById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service status not found with id: " + id));
        return ResponseEntity.ok(serviceStatusService.convertToDTO(serviceStatus));
    }
    
    @GetMapping("/name/{serviceName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceStatusDTO> getServiceStatusByName(@PathVariable String serviceName) {
        ServiceStatus serviceStatus = serviceStatusService.getServiceStatusByName(serviceName)
                .orElseThrow(() -> new ResourceNotFoundException("Service status not found with name: " + serviceName));
        return ResponseEntity.ok(serviceStatusService.convertToDTO(serviceStatus));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceStatusDTO> createServiceStatus(@Valid @RequestBody ServiceStatusDTO serviceStatusDTO) {
        ServiceStatus serviceStatus = serviceStatusService.createServiceStatus(serviceStatusDTO);
        return new ResponseEntity<>(serviceStatusService.convertToDTO(serviceStatus), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceStatusDTO> updateServiceStatus(
            @PathVariable Long id, 
            @Valid @RequestBody ServiceStatusDTO serviceStatusDTO) {
        ServiceStatus serviceStatus = serviceStatusService.updateServiceStatus(id, serviceStatusDTO);
        return ResponseEntity.ok(serviceStatusService.convertToDTO(serviceStatus));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteServiceStatus(@PathVariable Long id) {
        serviceStatusService.deleteServiceStatus(id);
        return ResponseEntity.ok(new ApiResponse<String>(true, "Service status deleted successfully"));
    }
    
    @PostMapping("/check-health")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> checkAllServicesHealth() {
        serviceStatusService.checkAllServicesHealth();
        return ResponseEntity.ok(new ApiResponse<String>(true, "Service health check triggered"));
    }
} 