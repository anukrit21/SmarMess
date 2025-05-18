package com.demoApp.delivery.service;

import com.demoApp.delivery.entity.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendDeliveryAssignedNotification(Delivery delivery) {
        log.info("Sending delivery assigned notification for delivery ID: {}", delivery.getId());
        // Implement actual notification logic here
    }

    @Override
    public void sendDeliveryStatusUpdateNotification(Delivery delivery) {
        log.info("Sending delivery status update notification for delivery ID: {}", delivery.getId());
        // Implement actual notification logic here
    }

    @Override
    public void sendDeliveryLocationUpdateNotification(Delivery delivery) {
        log.info("Sending delivery location update notification for delivery ID: {}", delivery.getId());
        // Implement actual notification logic here
    }

    @Override
    public void sendDeliveryCompletedNotification(Delivery delivery) {
        log.info("Sending delivery completed notification for delivery ID: {}", delivery.getId());
        // Implement actual notification logic here
    }

    @Override
    public void sendDeliveryCancelledNotification(Delivery delivery) {
        log.info("Sending delivery cancelled notification for delivery ID: {}", delivery.getId());
        // Implement actual notification logic here
    }
}
