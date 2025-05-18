package com.demoApp.admin.repository;

import com.demoApp.admin.entity.User;
import com.demoApp.admin.entity.UserRole;
import com.demoApp.admin.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRoleAndStatus(UserRole role, UserStatus status);
    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
} 