package com.demoApp.delivery.service;

import com.demoApp.delivery.entity.Delivery;

public interface NotificationService {
    void sendDeliveryAssignedNotification(Delivery delivery);
    void sendDeliveryStatusUpdateNotification(Delivery delivery);
    void sendDeliveryLocationUpdateNotification(Delivery delivery);
    void sendDeliveryCompletedNotification(Delivery delivery);
    void sendDeliveryCancelledNotification(Delivery delivery);
}
