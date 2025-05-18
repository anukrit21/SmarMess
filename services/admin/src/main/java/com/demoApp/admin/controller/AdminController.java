package com.demoApp.admin.controller;

import com.demoApp.admin.dto.AdminDTO;
import com.demoApp.admin.dto.ApiResponse;
import com.demoApp.admin.dto.CategoryDTO;
import com.demoApp.admin.dto.ServiceStatusDTO;
import com.demoApp.admin.dto.SystemMetricDTO;
import com.demoApp.admin.dto.UserActivityDTO;
import com.demoApp.admin.dto.UserDTO;
import com.demoApp.admin.dto.UserCreateDTO;
import com.demoApp.admin.dto.UserUpdateDTO;
import com.demoApp.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // User Management Endpoints
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        log.info("Received request to create user: {}", userCreateDTO.getEmail());
        return ResponseEntity.ok(ApiResponse.success(adminService.createUser(userCreateDTO)));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        log.info("Received request to update user: {}", userId);
        return ResponseEntity.ok(ApiResponse.success(adminService.updateUser(userId, userUpdateDTO)));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        log.info("Received request to delete user: {}", userId);
        adminService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsers(Pageable pageable) {
        log.info("Received request to get all users");
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllUsers(pageable)));
    }

    // Category Management Endpoints
    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("Received request to create category: {}", categoryDTO.getName());
        return ResponseEntity.ok(ApiResponse.success(adminService.createCategory(categoryDTO)));
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        log.info("Received request to update category: {}", categoryId);
        return ResponseEntity.ok(ApiResponse.success(adminService.updateCategory(categoryId, categoryDTO)));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        log.info("Received request to delete category: {}", categoryId);
        adminService.deleteCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully"));
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<Page<CategoryDTO>>> getAllCategories(Pageable pageable) {
        log.info("Received request to get all categories");
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllCategories(pageable)));
    }

    // System Monitoring Endpoints
    @GetMapping("/services/status")
    public ResponseEntity<ApiResponse<List<ServiceStatusDTO>>> getServiceStatus() {
        log.info("Received request to get service status");
        return ResponseEntity.ok(ApiResponse.success(adminService.getServiceStatus()));
    }

    @GetMapping("/system/metrics")
    public ResponseEntity<ApiResponse<List<SystemMetricDTO>>> getSystemMetrics() {
        log.info("Received request to get system metrics");
        return ResponseEntity.ok(ApiResponse.success(adminService.getSystemMetrics()));
    }

    @GetMapping("/activities")
    public ResponseEntity<ApiResponse<List<UserActivityDTO>>> getRecentUserActivities() {
        log.info("Received request to get recent user activities");
        return ResponseEntity.ok(ApiResponse.success(adminService.getRecentUserActivities()));
    }

    // Admin Management Endpoints
    @PostMapping("/admins")
    public ResponseEntity<ApiResponse<AdminDTO>> createAdmin(@RequestBody AdminDTO adminDTO) {
        log.info("Received request to create admin: {}", adminDTO.getEmail());
        return ResponseEntity.ok(ApiResponse.success(adminService.createAdmin(adminDTO)));
    }

    @PutMapping("/admins/{adminId}")
    public ResponseEntity<ApiResponse<AdminDTO>> updateAdmin(
            @PathVariable Long adminId,
            @RequestBody AdminDTO adminDTO) {
        log.info("Received request to update admin: {}", adminId);
        return ResponseEntity.ok(ApiResponse.success(adminService.updateAdmin(adminId, adminDTO)));
    }

    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Long adminId) {
        log.info("Received request to delete admin: {}", adminId);
        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok(ApiResponse.success("Admin deleted successfully"));
    }

    @GetMapping("/admins")
    public ResponseEntity<ApiResponse<Page<AdminDTO>>> getAllAdmins(Pageable pageable) {
        log.info("Received request to get all admins");
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllAdmins(pageable)));
    }
}
