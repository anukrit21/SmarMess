package com.demoApp.payment.repository;

import com.demoApp.payment.entity.Payment;
import com.demoApp.payment.entity.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Payment entity
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Find payment by payment ID
     */


    /**
    /**
     * Find all payments by user ID
     */
    Page<Payment> findByUserId(Long userId, Pageable pageable);

    /**
     * Find all payments by merchant ID
     */


    /**
     * Find all payments by owner ID
     */


    /**
     * Find all payments by user ID and status
     */
    Page<Payment> findByUserIdAndStatus(Long userId, PaymentStatus status, Pageable pageable);

    /**
     * Find all payments by merchant ID and status
     */


    /**
     * Find all payments by status
     */
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);

    /**
     * Find all payments created between dates
     */
    Page<Payment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find all payments by user ID and created between dates
     */
    Page<Payment> findByUserIdAndCreatedAtBetween(
            Long userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find all payments by merchant ID and created between dates
     */


    /**
     * Calculate total amount of completed payments for a user
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.userId = :userId AND p.status = 'COMPLETED'")
    BigDecimal calculateTotalCompletedAmountForUser(@Param("userId") Long userId);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.userId = :userId AND p.status = :status")
    BigDecimal calculateTotalAmountForUserByStatus(@Param("userId") Long userId, @Param("status") PaymentStatus status);

    /**
     * Calculate total amount of completed payments for a merchant
     */


    /**
     * Sum amount by status and date range
     */
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = :status AND p.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByStatusAndDateRange(@Param("status") PaymentStatus status, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Find recent payments by user
     */
    @Query("SELECT p FROM Payment p WHERE p.userId = :userId ORDER BY p.createdAt DESC")
    List<Payment> findRecentPaymentsByUserId(@Param("userId") Long userId, Pageable pageable);

    /**
     * Find recent payments by merchant
     */


    /**
     * Find payments by order reference
     */


    /**
     * Find payments by product ID
     */


    /**
     * Find payments by subscription ID
     */


    List<Payment> findByUserId(Long userId);
    List<Payment> findByOrderId(Long orderId);
    List<Payment> findByStatus(PaymentStatus status);

    @Query("SELECT p FROM Payment p WHERE p.status = :status AND p.createdAt BETWEEN :startDate AND :endDate")
    Page<Payment> findByStatusAndDateRange(
            @Param("status") PaymentStatus status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status AND p.createdAt BETWEEN :startDate AND :endDate")
    Long countByStatusAndDateRange(
            @Param("status") PaymentStatus status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT p FROM Payment p WHERE p.userId = :userId AND p.status = :status")
    List<Payment> findByUserIdAndStatus(
            @Param("userId") String userId,
            @Param("status") PaymentStatus status
    );

    @Query("SELECT p FROM Payment p WHERE p.orderId = :orderId AND p.status = :status")
    List<Payment> findByOrderIdAndStatus(
            @Param("orderId") String orderId,
            @Param("status") PaymentStatus status
    );

    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByUserIdAndStatus(Long userId, PaymentStatus status);
    List<Payment> findByOrderIdAndStatus(Long orderId, PaymentStatus status);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    Double sumAmountByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT AVG(p.amount) FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    Double avgAmountByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status AND p.createdAt BETWEEN :startDate AND :endDate")
    long countByStatusAndCreatedAtBetween(PaymentStatus status, LocalDateTime startDate, LocalDateTime endDate);

    Optional<Payment> findByPaymentIntentId(String paymentIntentId);
    List<Payment> findByCustomerId(String customerId);
    Optional<Payment> findByDisputeId(String disputeId);
} 