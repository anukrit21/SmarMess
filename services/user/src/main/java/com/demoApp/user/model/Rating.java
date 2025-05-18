package com.demoApp.user.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Long itemId;
    private String itemType;
    private int rating;
    private String review;
    private LocalDateTime updatedAt;

    public Rating(Long itemId, String itemType, int rating, String review) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.rating = rating;
        this.review = review;
        this.updatedAt = LocalDateTime.now();
    }
}
