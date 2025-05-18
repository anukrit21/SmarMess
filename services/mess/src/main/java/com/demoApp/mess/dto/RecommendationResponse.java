package com.demoApp.mess.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {
    public void setCategory(String category) { this.category = category; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    // Lombok @Builder now present for builder pattern

    private Long messId;
    private String messName;
    private String category;
    private String cuisine;
    private Double score;
    private String address;
    private String location;
    private String[] recommendedItems;

    public Long getMessId() {
        return messId;
    }

    public String getMessName() {
        return messName;
    }

    public String getCategory() {
        return category;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public String[] getRecommendedItems() {
        return recommendedItems;
    }

    public Double getScore() {
        return score;
    }
}
