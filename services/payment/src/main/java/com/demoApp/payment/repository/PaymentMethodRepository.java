package com.demoApp.payment.repository;

import com.demoApp.payment.entity.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for payment method operations
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    /**
     * Find all active payment methods for a user
     */
    List<PaymentMethod> findByUserIdAndIsActiveTrue(Long userId);

    /**
     * Check if a user has any active payment methods
     */
    boolean existsByUserIdAndIsActiveTrue(Long userId);

    /**
     * Find a user's default payment method
     */
    Optional<PaymentMethod> findByUserIdAndIsDefaultTrueAndIsActiveTrue(Long userId);

    /**
     * Find by Stripe payment method ID
     */
    Optional<PaymentMethod> findByStripePaymentMethodIdAndIsActiveTrue(String stripePaymentMethodId);

    /**
     * Find all payment methods by user ID
     */
    List<PaymentMethod> findByUserId(Long userId);

    /**
     * Find all default payment methods for a user
     */
    List<PaymentMethod> findByUserIdAndIsDefaultTrue(Long userId);

    /**
     * Count default payment methods for a user
     */
    Integer countByUserIdAndIsDefaultTrue(Long userId);

    /**
     * Find the most recently created active payment method for a user
     */
    Optional<PaymentMethod> findFirstByUserIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId);

    /**
     * Find payment methods by type
     */
    Page<PaymentMethod> findByType(String type, Pageable pageable);

    /**
     * Find payment methods by user ID and type
     */
    List<PaymentMethod> findByUserIdAndType(Long userId, String type);

    /**
     * Find payment methods by card brand
     */
    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.brand = :cardBrand AND pm.isActive = true")
    Page<PaymentMethod> findActiveByCardBrand(@Param("cardBrand") String cardBrand, Pageable pageable);
} 