package com.demoApp.order.repository;

import com.demoApp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByMessId(Long messId);
    List<Order> findByOrderTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
} 