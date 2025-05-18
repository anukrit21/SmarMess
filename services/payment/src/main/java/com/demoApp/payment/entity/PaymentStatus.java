package com.demoApp.payment.entity;

public enum PaymentStatus {
    PENDING,
    PROCESSING,
    SUCCEEDED,
    FAILED,
    REFUNDED,
    PARTIALLY_REFUNDED,
    CANCELLED,
    DISPUTED,
    COMPLETED,
    UNKNOWN
} 