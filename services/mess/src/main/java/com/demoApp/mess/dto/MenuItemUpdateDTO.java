package com.demoApp.mess.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for updating menu items
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemUpdateDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;
    
    private Boolean isVegetarian;
    private Boolean isSpicy;
    private Boolean isAvailable;
    private Integer preparationTime;
    private Long categoryId;
    private List<AddOnDTO> addOns;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getPrice() {
        return price != null ? price.doubleValue() : null;
    }
    
    public BigDecimal getPriceBigDecimal() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price != null ? BigDecimal.valueOf(price) : null;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Boolean getIsVegetarian() {
        return isVegetarian;
    }
    
    public void setIsVegetarian(Boolean vegetarian) {
        isVegetarian = vegetarian;
    }
    
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    
    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }
    
    public Boolean getIsSpicy() {
        return isSpicy;
    }
    
    public void setIsSpicy(Boolean spicy) {
        isSpicy = spicy;
    }
    
    public Integer getPreparationTime() {
        return preparationTime;
    }
    
    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }
}