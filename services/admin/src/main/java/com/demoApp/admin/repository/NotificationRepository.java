package com.demoApp.admin.repository;

import com.demoApp.admin.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdAndIsActiveTrue(String recipientId);
    List<Notification> findByRecipientIdAndIsReadFalseAndIsActiveTrue(String recipientId);
    long countByRecipientIdAndIsReadFalseAndIsActiveTrue(String recipientId);
    List<Notification> findAllByRecipientId(String recipientId);
    List<Notification> findAllByRecipientIdAndIsReadFalse(String recipientId);
    List<Notification> findByRecipientIdAndIsReadFalse(String recipientId);
    Page<Notification> findAll(Pageable pageable);
}