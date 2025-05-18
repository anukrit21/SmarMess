package com.demoApp.subscription.repository;

import com.demoApp.subscription.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    List<UserSubscription> findByUserId(Long userId);
    
    @Query("SELECT us FROM UserSubscription us WHERE us.user.id = :userId AND us.isActive = true")
    Optional<UserSubscription> findByUserIdAndIsActiveTrue(@Param("userId") Long userId);
    
    @Query("SELECT us FROM UserSubscription us WHERE us.user.id = :userId AND us.status = 'ACTIVE' AND us.endDate >= :currentDate")
    List<UserSubscription> findActiveSubscriptionsByUserId(@Param("userId") Long userId, @Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT us FROM UserSubscription us WHERE us.status = 'ACTIVE' AND us.endDate < :currentDate")
    List<UserSubscription> findExpiredSubscriptions(@Param("currentDate") LocalDate currentDate);
} 