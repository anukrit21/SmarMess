package com.demoApp.mess.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOn {
    private Long id;
    
    private String name;
    private String description;
    private boolean isVeg;
    private double price;
    private boolean isAvailable;
    
    private Menu menu;
}
