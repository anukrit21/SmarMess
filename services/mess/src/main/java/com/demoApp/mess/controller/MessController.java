package com.demoApp.mess.controller;

import com.demoApp.mess.dto.MenuItemCreateDTO;
import com.demoApp.mess.dto.MenuItemDTO;
import com.demoApp.mess.dto.MenuItemUpdateDTO;
import com.demoApp.mess.dto.MessAnalyticsDTO;
import com.demoApp.mess.dto.MessCreateDTO;
import com.demoApp.mess.dto.MessDTO;
import com.demoApp.mess.dto.MessUpdateDTO;
import com.demoApp.mess.service.MessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mess")
@RequiredArgsConstructor
public class MessController {
    private final MessService messService;

    @PostMapping
    public ResponseEntity<MessDTO> createMess(@RequestBody MessCreateDTO request) {
        return ResponseEntity.ok(messService.createMess(request));
    }

    @PutMapping("/{messId}")
    public ResponseEntity<MessDTO> updateMess(
            @PathVariable Long messId,
            @RequestBody MessUpdateDTO request) {
        return ResponseEntity.ok(messService.updateMess(messId, request));
    }

    @PostMapping("/{messId}/menu")
    public ResponseEntity<MenuItemDTO> addMenuItem(
            @PathVariable Long messId,
            @RequestBody MenuItemCreateDTO request) {
        return ResponseEntity.ok(messService.addMenuItem(messId, request));
    }

    @PutMapping("/{messId}/menu/{menuItemId}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(
            @PathVariable Long messId,
            @PathVariable Long menuItemId,
            @RequestBody MenuItemUpdateDTO request) {
        return ResponseEntity.ok(messService.updateMenuItem(messId, menuItemId, request));
    }

    @GetMapping
    public ResponseEntity<List<MessDTO>> getAllMesses() {
        return ResponseEntity.ok(messService.getAllMesses());
    }

    @GetMapping("/{messId}/menu")
    public ResponseEntity<List<MenuItemDTO>> getMessMenu(@PathVariable Long messId) {
        return ResponseEntity.ok(messService.getMessMenu(messId));
    }

    @GetMapping("/{messId}/analytics")
    public ResponseEntity<MessAnalyticsDTO> getAnalytics(
            @PathVariable Long messId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(messService.getAnalytics(messId, startDate, endDate));
    }
}
