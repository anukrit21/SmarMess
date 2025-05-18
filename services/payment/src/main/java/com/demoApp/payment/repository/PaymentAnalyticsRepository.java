package com.demoApp.payment.repository;

import com.demoApp.payment.entity.PaymentAnalytics;
import com.demoApp.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;

@Repository
public interface PaymentAnalyticsRepository extends JpaRepository<PaymentAnalytics, Long> {
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = 'COMPLETED'")
    BigDecimal getTotalAmount();

    @Query("SELECT COUNT(p) FROM Payment p")
    Long getTotalTransactions();

    @Query("SELECT p.status as status, COUNT(p) as count FROM Payment p GROUP BY p.status")
    List<Object[]> getTransactionsByStatus(); // Returns Object[]{PaymentStatus status, Long count}

    @Query("SELECT p.paymentMethod as method, SUM(p.amount) as amount FROM Payment p WHERE p.status = 'COMPLETED' GROUP BY p.paymentMethod")
    List<Object[]> getAmountByPaymentMethod(); // Returns Object[]{PaymentMethodType method, BigDecimal amount}

    @Modifying
    @Query("UPDATE PaymentAnalytics a SET a.totalPayments = a.totalPayments + 1")
    void incrementTotalPayments();

    @Modifying
    @Query("UPDATE PaymentAnalytics a SET a.totalAmount = a.totalAmount + :amount")
    void incrementTotalAmount(@Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE PaymentAnalytics a SET a.totalRefunds = a.totalRefunds + 1")
    void incrementTotalRefunds();

    @Modifying
    @Query("UPDATE PaymentAnalytics a SET a.totalRefundAmount = a.totalRefundAmount + :amount")
    void incrementTotalRefundAmount(@Param("amount") BigDecimal amount);

    @Modifying
    @Query("UPDATE PaymentAnalytics a SET a.totalDisputes = a.totalDisputes + 1")
    void incrementTotalDisputes();

    // JPQL does not support updating map keys directly. This method is invalid and should be removed or implemented with native query or in Java logic.
    // @Modifying
    // @Query("UPDATE PaymentAnalytics a SET a.paymentMethodCounts[:method] = COALESCE(a.paymentMethodCounts[:method], 0) + 1")
    // void incrementPaymentMethodCount(@Param("method") String method);
}