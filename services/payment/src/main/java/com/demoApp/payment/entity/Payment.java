package com.demoApp.payment.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethodType paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "payment_intent_id")
    private String paymentIntentId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "upi_id")
    private String upiId;

    @Column(name = "stripe_payment_id")
    private String stripePaymentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "description")
    private String description;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "dispute_id")
    private String disputeId;

    @Column(name = "disputed_at")
    private LocalDateTime disputedAt;

    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "metadata")
    private String metadata;

    @ElementCollection
    @CollectionTable(name = "payment_metadata", joinColumns = @JoinColumn(name = "payment_id"))
    @MapKeyColumn(name = "metadata_key")
    @Column(name = "metadata_value")
    @Builder.Default
    private Map<String, String> metadataMap = new HashMap<>();

    // Status Handling Methods
    public void markAsCompleted() {
        this.status = PaymentStatus.COMPLETED;
    }

    public void markAsFailed() {
        this.status = PaymentStatus.FAILED;
    }

    public void markAsRefunded() {
        this.status = PaymentStatus.REFUNDED;
        this.refundedAt = LocalDateTime.now();
    }

    public void markAsDisputed(String disputeId) {
        this.status = PaymentStatus.DISPUTED;
        this.disputeId = disputeId;
        this.disputedAt = LocalDateTime.now();
    }

    // Metadata Conversion Methods
    public Map<String, String> getMetadataMap() {
        if (metadata != null && !metadata.isEmpty()) {
            try {
                return OBJECT_MAPPER.readValue(
                        metadata,
                        OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, String.class)
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse metadata", e);
            }
        }
        return new HashMap<>();
    }

    public void setMetadataMap(Map<String, String> metadataMap) {
        if (metadataMap != null && !metadataMap.isEmpty()) {
            try {
                this.metadata = OBJECT_MAPPER.writeValueAsString(metadataMap);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize metadata", e);
            }
        } else {
            this.metadata = null;
        }
    }

    public void setMetadataFromString(String metadataString) {
        if (metadataString != null && !metadataString.isEmpty()) {
            try {
                this.metadata = metadataString;
                this.metadataMap = getMetadataMap();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse metadata string", e);
            }
        }
    }

    // Lifecycle Callbacks
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        updateMetadata();
    }

    @PreUpdate
    public void updateMetadata() {
        if (metadataMap != null && !metadataMap.isEmpty()) {
            setMetadataMap(metadataMap);
        } else if (metadata != null && !metadata.isEmpty()) {
            metadataMap = getMetadataMap();
        }
    }
}
