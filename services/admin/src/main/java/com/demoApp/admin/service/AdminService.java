package com.demoApp.admin.service;

import com.demoApp.admin.dto.*;
import com.demoApp.admin.entity.*;
import com.demoApp.admin.exception.ResourceNotFoundException;
import com.demoApp.admin.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final ServiceStatusRepository serviceStatusRepository;
    private final SystemMetricRepository systemMetricRepository;
    private final UserActivityRepository userActivityRepository;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers(UserRole role, UserStatus status) {
        log.info("Fetching users with role: {} and status: {}", role, status);
        if (role != null && status != null) {
            return userRepository.findByRoleAndStatus(role, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        } else if (role != null) {
            return userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        } else if (status != null) {
            return userRepository.findAll().stream()
                .filter(user -> user.getStatus() == status)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        } else {
            return getAllUsers();
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Transactional
    public UserDTO createUser(UserCreateDTO request) {
        log.info("Creating new user: {}", request.getUsername());
        validateUserCreateRequest(request);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole(UserRole.valueOf(request.getRole()));
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(generateRandomPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        log.info("Created new user with ID: {}", savedUser.getId());

        sendWelcomeNotification(savedUser);

        return convertToDTO(savedUser);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserUpdateDTO request) {
        log.info("Updating user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getRole() != null) {
            user.setRole(UserRole.valueOf(request.getRole()));
        }
        if (request.getStatus() != null) {
            user.setStatus(UserStatus.valueOf(request.getStatus()));
        }

        User updatedUser = userRepository.save(user);
        log.info("Updated user with ID: {}", userId);

        if (request.getStatus() != null) {
            sendStatusUpdateNotification(updatedUser);
        }

        return convertToDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        log.info("Soft deleted user with ID: {}", userId);

        sendDeletionNotification(user);
    }

    @Transactional
    public UserDTO activateUser(Long userId) {
        log.info("Activating user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setStatus(UserStatus.ACTIVE);
        user.setLocked(false);
        User updatedUser = userRepository.save(user);
        log.info("Activated user with ID: {}", userId);

        sendStatusUpdateNotification(updatedUser);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public UserDTO deactivateUser(Long userId) {
        log.info("Deactivating user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setStatus(UserStatus.INACTIVE);
        User updatedUser = userRepository.save(user);
        log.info("Deactivated user with ID: {}", userId);

        sendStatusUpdateNotification(updatedUser);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public UserDTO lockUser(Long userId) {
        log.info("Locking user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setLocked(true);
        User updatedUser = userRepository.save(user);
        log.info("Locked user with ID: {}", userId);

        sendStatusUpdateNotification(updatedUser);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public UserDTO unlockUser(Long userId) {
        log.info("Unlocking user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setLocked(false);
        User updatedUser = userRepository.save(user);
        log.info("Unlocked user with ID: {}", userId);

        sendStatusUpdateNotification(updatedUser);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public void resetUserPassword(Long userId) {
        log.info("Resetting password for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        String newPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Reset password for user with ID: {}", userId);

        sendPasswordResetNotification(user, newPassword);
    }

    @Transactional(readOnly = true)
    public AdminAnalyticsDTO getAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching analytics data from {} to {}", startDate, endDate);
        
        // Get user analytics
        UserAnalyticsDTO userAnalytics = getUserAnalytics(startDate, endDate);
        
        // Build and return the complete analytics DTO
        return AdminAnalyticsDTO.builder()
                .totalUsers(Long.valueOf(userAnalytics.getTotalUsers()))  // Convert int to Long
                .userAnalytics(userAnalytics)
                // Other analytics can be added here as needed
                .build();
    }
    
    private UserAnalyticsDTO getUserAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        // Get all users once and reuse the list
        List<User> allUsers = userRepository.findAll();
        
        // Get total users
        int totalUsers = allUsers.size();
        
        // Get active users (users that are not DELETED)
        int activeUsers = (int) allUsers.stream()
                .filter(user -> user.getStatus() != UserStatus.DELETED)
                .count();
        
        // Get users by role
        Map<String, Long> roleDistribution = allUsers.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getRole().name(),
                        Collectors.counting()
                ));
        
        // Get users by status
        Map<String, Long> statusDistribution = allUsers.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getStatus().name(),
                        Collectors.counting()
                ));
        
        // Initialize newUsers count
        int newUsers = 0;
        
        // Get new users in date range if dates are provided
        if (startDate != null && endDate != null) {
            newUsers = (int) allUsers.stream()
                    .filter(user -> !user.getCreatedAt().isBefore(startDate) && 
                                  !user.getCreatedAt().isAfter(endDate))
                    .count();
        }
        
        return UserAnalyticsDTO.builder()
                .totalUsers(totalUsers)
                .activeUsers(activeUsers)
                .newUsers(newUsers)
                .roleDistribution(roleDistribution)
                .statusDistribution(statusDistribution)
                .build();
    }

    private void validateUserCreateRequest(UserCreateDTO request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone())
            .role(user.getRole().name())
            .status(user.getStatus().name())
            .locked(user.isLocked())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .deletedAt(user.getDeletedAt())
            .build();
    }

    private void sendWelcomeNotification(User user) {
        String message = String.format("Welcome to DemoApp, %s! Your account has been created.", user.getName());
        notificationService.sendNotification(
                user.getId().toString(),
                "Welcome to DemoApp",
                message,
                NotificationType.WELCOME
        );
    }

    private void sendStatusUpdateNotification(User user) {
        String message = String.format("Your account status has been updated to: %s", user.getStatus());
        notificationService.sendNotification(
                user.getId().toString(),
                "Account Status Update",
                message,
                NotificationType.STATUS_UPDATE
        );
    }

    private void sendDeletionNotification(User user) {
        String message = "Your account has been deleted. Thank you for using DemoApp.";
        notificationService.sendNotification(
                user.getId().toString(),
                "Account Deleted",
                message,
                NotificationType.ACCOUNT_DELETED
        );
    }

    private void sendPasswordResetNotification(User user, String newPassword) {
        String message = String.format("Your password has been reset. Your new password is: %s", newPassword);
        notificationService.sendNotification(
                user.getId().toString(),
                "Password Reset",
                message,
                NotificationType.PASSWORD_RESET
        );
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        log.info("Fetching all users with pagination");
        return userRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        log.info("Fetching all categories with pagination");
        return categoryRepository.findAll(pageable).map(category -> CategoryDTO.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .createdAt(category.getCreatedAt())
            .build());
    }

    @Transactional(readOnly = true)
    public List<ServiceStatusDTO> getServiceStatus() {
        log.info("Fetching service status");
        return serviceStatusRepository.findAll().stream()
                .map(this::convertToServiceStatusDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SystemMetricDTO> getSystemMetrics() {
        log.info("Fetching system metrics");
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "timestamp"));
        return systemMetricRepository.findAll(pageable).stream()
                .map(SystemMetricDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    private ServiceStatusDTO convertToServiceStatusDTO(ServiceStatus status) {
        return ServiceStatusDTO.builder()
                .id(status.getId())
                .serviceName(status.getServiceName())
                .serviceUrl(status.getServiceUrl())
                .version(status.getVersion())
                .healthDetails(status.getHealthDetails())
                .status(status.getStatus().name())
                .responseTime(status.getResponseTime())
                .errorCount(status.getErrorCount())
                .lastChecked(status.getLastChecked())
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserActivityDTO> getRecentUserActivities() {
        log.info("Fetching recent user activities");
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "timestamp"));
        return userActivityRepository.findAll(pageable).stream()
                .map(this::convertToUserActivityDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserActivityDTO> getRecentActivities() {
        log.info("Fetching recent activities");
        // This appears to be a duplicate of getRecentUserActivities, but keeping both for backward compatibility
        return getRecentUserActivities();
    }
    
    private UserActivityDTO convertToUserActivityDTO(UserActivity activity) {
        return UserActivityDTO.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .username(activity.getUsername())
                .activityType(activity.getActivityType())
                .description(activity.getDescription())
                .ipAddress(activity.getIpAddress())
                .timestamp(activity.getTimestamp())
                .module(activity.getServiceName()) // Map serviceName to module
                .build();
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category = categoryRepository.save(category);
        return CategoryDTO.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .createdAt(category.getCreatedAt())
            .build();
    }

    @Transactional
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category = categoryRepository.save(category);
        return CategoryDTO.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .createdAt(category.getCreatedAt())
            .build();
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
    }

    @Transactional
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        log.info("Creating new admin: {}", adminDTO.getEmail());
        Admin admin = Admin.builder()
            .email(adminDTO.getEmail())
            .name(adminDTO.getName())
            .username(adminDTO.getName().toLowerCase().replaceAll("\\s+", "."))
            .role(adminDTO.getRole())
            .enabled(true)
            .password(passwordEncoder.encode(adminDTO.getPassword()))
            .build();
        admin = adminRepository.save(admin);
        return AdminDTO.builder()
            .id(admin.getId())
            .name(admin.getName())
            .email(admin.getEmail())
            .role(admin.getRole())
            .isActive(admin.isEnabled())
            .createdAt(admin.getCreatedAt())
            .build();
    }

    @Transactional
    public AdminDTO updateAdmin(Long adminId, AdminDTO adminDTO) {
        log.info("Updating admin: {}", adminId);
        Admin admin = adminRepository.findById(adminId)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setRole(adminDTO.getRole());
        if (adminDTO.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        }
        
        admin = adminRepository.save(admin);
        return AdminDTO.builder()
            .id(admin.getId())
            .name(admin.getName())
            .email(admin.getEmail())
            .role(admin.getRole())
            .isActive(admin.isEnabled())
            .createdAt(admin.getCreatedAt())
            .build();
    }

    @Transactional
    public void deleteAdmin(Long adminId) {
        log.info("Deleting admin: {}", adminId);
        Admin admin = adminRepository.findById(adminId)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + adminId));
        adminRepository.delete(admin);
    }

    @Transactional(readOnly = true)
    public Page<AdminDTO> getAllAdmins(Pageable pageable) {
        log.info("Fetching all admins with pagination");
        return adminRepository.findAll(pageable).map(admin -> AdminDTO.builder()
            .id(admin.getId())
            .name(admin.getName())
            .email(admin.getEmail())
            .role(admin.getRole())
            .isActive(admin.isEnabled())
            .createdAt(admin.getCreatedAt())
            .build());
    }


}
