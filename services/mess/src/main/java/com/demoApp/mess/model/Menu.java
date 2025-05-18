package com.demoApp.mess.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.demoApp.mess.entity.Mess;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private Long id;

    private String name;
    private String description;
    private boolean isVeg;
    private double basePrice;
    private String imageUrl;
    private boolean isAvailable;
    private String category;

    @Builder.Default
    private List<AddOn> availableAddOns = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    private Mess mess;

    private MealType mealType;

    @Builder.Default
    private int orderCount = 0;

    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACKS
    }
}
