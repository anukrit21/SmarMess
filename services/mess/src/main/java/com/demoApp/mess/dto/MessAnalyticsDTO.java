package com.demoApp.mess.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessAnalyticsDTO {
    private int totalMenuItems;
    private int availableItems;
    private Map<String, Long> categoryDistribution;
    private Map<String, Long> priceRangeDistribution;
    private double vegetarianRatio;
    
    public int getTotalMenuItems() {
        return totalMenuItems;
    }
    
    public void setTotalMenuItems(int totalMenuItems) {
        this.totalMenuItems = totalMenuItems;
    }
    
    public int getAvailableItems() {
        return availableItems;
    }
    
    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }
    
    public Map<String, Long> getCategoryDistribution() {
        return categoryDistribution;
    }
    
    public void setCategoryDistribution(Map<String, Long> categoryDistribution) {
        this.categoryDistribution = categoryDistribution;
    }
    
    public Map<String, Long> getPriceRangeDistribution() {
        return priceRangeDistribution;
    }
    
    public void setPriceRangeDistribution(Map<String, Long> priceRangeDistribution) {
        this.priceRangeDistribution = priceRangeDistribution;
    }
    
    public double getVegetarianRatio() {
        return vegetarianRatio;
    }
    
    public void setVegetarianRatio(double vegetarianRatio) {
        this.vegetarianRatio = vegetarianRatio;
    }
}