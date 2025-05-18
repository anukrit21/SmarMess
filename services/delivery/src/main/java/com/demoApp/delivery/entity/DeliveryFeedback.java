package com.demoApp.delivery.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "delivery_feedback")
public class DeliveryFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;

    private double rating;
    private String feedback;
    private LocalDateTime createdAt;

    public DeliveryFeedback() {}

    public DeliveryFeedback(double rating, String feedback, LocalDateTime createdAt) {
        this.rating = rating;
        this.feedback = feedback;
        this.createdAt = createdAt;
    }
} 