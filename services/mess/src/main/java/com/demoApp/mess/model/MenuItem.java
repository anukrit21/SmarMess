package com.demoApp.mess.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private Long id;
    
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    private String imageUrl;
    
    private boolean isVegetarian;
    
    private boolean isSpicy;
    
    private boolean isAvailable;
    
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    private Integer preparationTime;
    
    private Menu menu;
    
    // List of AddOns for this menu item

    @Builder.Default
    private List<AddOn> addOns = new ArrayList<>();
    
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
