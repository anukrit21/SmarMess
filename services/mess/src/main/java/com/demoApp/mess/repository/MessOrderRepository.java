package com.demoApp.mess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demoApp.mess.entity.MessOrder;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessOrderRepository extends JpaRepository<MessOrder, Long> {
    List<MessOrder> findByMess_Id(Long messId);
    List<MessOrder> findByUserId(Long userId);
    List<MessOrder> findByUserIdAndMess_Id(Long userId, Long messId);
    List<MessOrder> findByMess_IdAndStatus(Long messId, MessOrder.OrderStatus status);
    List<MessOrder> findByMess_IdAndOrderDateBetween(Long messId, LocalDateTime startDate, LocalDateTime endDate);
    
    // Count orders for a mess between dates with specific status
    Long countByMess_IdAndOrderDateBetweenAndStatus(Long messId, LocalDateTime startDate, LocalDateTime endDate, MessOrder.OrderStatus status);
}
