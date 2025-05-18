package com.demoApp.mess.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.demoApp.mess.entity.MenuItem}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Boolean isVegetarian;
    private Boolean isSpicy;
    private Boolean isAvailable;
    private Long categoryId;
    private String categoryName;
    private List<AddOnDTO> addOns;
    private Integer preparationTime;
    private Long menuId;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Explicit getters and setters for boolean fields to handle 'is' prefix
    public Boolean getVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    // Convenience method for boolean fields
    public boolean isVegetarian() {
        return Boolean.TRUE.equals(isVegetarian);
    }

    public boolean isSpicy() {
        return Boolean.TRUE.equals(isSpicy);
    }

    public boolean isAvailable() {
        return Boolean.TRUE.equals(isAvailable);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Boolean getSpicy() {
        return isSpicy;
    }
    
    public void setSpicy(boolean spicy) {
        isSpicy = spicy;
    }
    
    public Integer getPreparationTime() {
        return preparationTime;
    }
    
    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }
    
    public Long getMenuId() {
        return menuId;
    }
    
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    
    public List<AddOnDTO> getAddOns() {
        return addOns;
    }
    
    public void setAddOns(List<AddOnDTO> addOns) {
        this.addOns = addOns;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy != null ? createdBy.toString() : null;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy != null ? updatedBy.toString() : null;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}