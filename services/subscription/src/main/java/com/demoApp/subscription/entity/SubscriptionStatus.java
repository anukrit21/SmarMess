package com.demoApp.subscription.entity;

public enum SubscriptionStatus {
    ACTIVE,
    PENDING,
    CANCELLED,
    EXPIRED,
    SUSPENDED,
    FAILED;

    // Optional: You can add a custom method if needed for specific string values.
    public static SubscriptionStatus fromString(String status) {
        switch (status.toUpperCase()) {
            case "PENDING":
                return PENDING;
            case "ACTIVE":
                return ACTIVE;
            case "CANCELLED":
                return CANCELLED;
            case "EXPIRED":
                return EXPIRED;
            case "SUSPENDED":
                return SUSPENDED;
            case "FAILED":
                return FAILED;
            default:
                throw new IllegalArgumentException("Unknown status: " + status);
        }
    }
}
