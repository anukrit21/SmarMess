package com.demoApp.mess.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MenuDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private Long messId;
    private List<MenuItemDTO> menuItems;
    private BigDecimal price;
    private MealType mealType;
    private Boolean available;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public MenuDTO() {
    }
    
    public MenuDTO(Long id, String name, String description, Boolean isActive, Long messId,
            List<MenuItemDTO> menuItems, BigDecimal price, MealType mealType, Boolean available,
            String createdBy, String updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.messId = messId;
        this.menuItems = menuItems;
        this.price = price;
        this.mealType = mealType;
        this.available = available;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Boolean getActive() {
        return isActive;
    }
    
    public void setActive(Boolean active) {
        this.isActive = active;
    }
    
    public Long getMessId() {
        return messId;
    }
    
    public void setMessId(Long messId) {
        this.messId = messId;
    }
    
    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }
    
    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public MealType getMealType() {
        return mealType;
    }
    
    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
    
    public Boolean getAvailable() {
        return available;
    }
    
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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
    
    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACK
    }
}
