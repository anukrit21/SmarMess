package com.demoApp.campus_module.service;

import com.demoApp.campus_module.dto.*;
import com.demoApp.campus_module.entity.*;
import com.demoApp.campus_module.repository.*;
import com.demoApp.campus_module.client.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampusService {
    private final CampusRepository campusRepository;
    private final BuildingRepository buildingRepository;
    private final CampusEventRepository eventRepository;
    private final NotificationService notificationService;

    // Existing campus management methods...

    @Transactional
    public CampusEventDTO createEvent(Long campusId, EventCreateDTO request) {
        Campus campus = campusRepository.findById(campusId)
            .orElseThrow(() -> new RuntimeException("Campus not found with ID: " + campusId));

        Building location = buildingRepository.findById(request.getLocationId())
            .orElseThrow(() -> new RuntimeException("Location not found with ID: " + request.getLocationId()));

        CampusEvent event = new CampusEvent();
        event.setCampus(campus);
        event.setLocation(location);
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setType(EventType.valueOf(request.getType()));
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());

        CampusEvent savedEvent = eventRepository.save(event);
        log.info("Created new event at campus {}: {}", campusId, savedEvent.getName());

        // Send event notification
        sendEventNotification(savedEvent);

        return convertToEventDTO(savedEvent);
    }

    @Transactional(readOnly = true)
    public List<CampusEventDTO> getCampusEvents(Long campusId, LocalDateTime startDate, LocalDateTime endDate) {
        return eventRepository.findByCampusIdAndStartTimeBetween(campusId, startDate, endDate).stream()
            .map(this::convertToEventDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CampusAnalyticsDTO getAnalytics(Long campusId, LocalDateTime startDate, LocalDateTime endDate) {
        List<CampusEvent> events = eventRepository.findByCampusIdAndStartTimeBetween(campusId, startDate, endDate);
        
        return CampusAnalyticsDTO.builder()
            .totalEvents(events.size())
            .eventTypeDistribution(getEventTypeDistribution(events))
            .locationUtilization(getLocationUtilization(events))
            .build();
    }

    private void sendEventNotification(CampusEvent event) {
        String message = String.format("New event '%s' at %s on %s", 
            event.getName(), 
            event.getLocation().getName(),
            event.getStartTime());

        notificationService.sendNotification(
            event.getCampus().getId(),
            "New Campus Event",
            message,
            NotificationType.EVENT
        );
    }

    private Map<String, Long> getEventTypeDistribution(List<CampusEvent> events) {
        return events.stream()
            .collect(Collectors.groupingBy(e -> e.getType().name(), Collectors.counting()));
    }

    private Map<String, Long> getLocationUtilization(List<CampusEvent> events) {
        return events.stream()
            .collect(Collectors.groupingBy(e -> e.getLocation().getName(), Collectors.counting()));
    }

    private CampusEventDTO convertToEventDTO(CampusEvent event) {
        return CampusEventDTO.builder()
            .id(event.getId())
            .campusId(event.getCampus().getId())
            .locationId(event.getLocation().getId())
            .name(event.getName())
            .description(event.getDescription())
            .type(event.getType().name())
            .startTime(event.getStartTime())
            .endTime(event.getEndTime())
            .createdAt(event.getCreatedAt())
            .build();
    }
} 