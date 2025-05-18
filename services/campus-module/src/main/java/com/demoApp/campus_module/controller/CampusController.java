package com.demoApp.campus_module.controller;

import com.demoApp.campus_module.dto.*;
import com.demoApp.campus_module.service.CampusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/campuses")
@RequiredArgsConstructor
public class CampusController {
    private final CampusService campusService;

    @PostMapping("/{campusId}/events")
    public ResponseEntity<CampusEventDTO> createEvent(
            @PathVariable Long campusId,
            @Valid @RequestBody EventCreateDTO request) {
        return ResponseEntity.ok(campusService.createEvent(campusId, request));
    }

    @GetMapping("/{campusId}/events")
    public ResponseEntity<List<CampusEventDTO>> getCampusEvents(
            @PathVariable Long campusId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(campusService.getCampusEvents(campusId, startDate, endDate));
    }

    @GetMapping("/{campusId}/analytics")
    public ResponseEntity<CampusAnalyticsDTO> getCampusAnalytics(
            @PathVariable Long campusId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(campusService.getAnalytics(campusId, startDate, endDate));
    }
} 