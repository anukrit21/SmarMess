package com.demoApp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesDTO {
    private String preferredCuisine;
    private String deliveryTime; 
    private boolean subscribeToNewsletter;
    private String language;  
    private String timezone;  
}
