package com.demoApp.subscription.repository;

import com.demoApp.subscription.entity.Subscription;
import com.demoApp.subscription.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    // Basic queries
    List<Subscription> findByUserId(Long userId);
    List<Subscription> findByStatus(SubscriptionStatus status);
    List<Subscription> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Optional<Subscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status);

    // Analytics queries
    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.createdAt BETWEEN ?1 AND ?2")
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT s.status as status, COUNT(s) as count FROM Subscription s WHERE s.createdAt BETWEEN ?1 AND ?2 GROUP BY s.status")
    List<Object[]> countByStatusAndCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT SUM(s.plan.price) FROM Subscription s WHERE s.createdAt BETWEEN ?1 AND ?2")
    BigDecimal sumAmountByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT s.plan as plan, COUNT(s) as count FROM Subscription s WHERE s.createdAt BETWEEN ?1 AND ?2 GROUP BY s.plan")
    List<Object[]> countByPlanAndCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
