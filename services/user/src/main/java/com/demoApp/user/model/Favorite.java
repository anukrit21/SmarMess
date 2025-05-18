package com.demoApp.user.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private String itemId;
    private String itemName;
    private String itemType;
    private String itemImageUrl;

    public Favorite(Long itemId, String itemType) {
        this.itemId = itemId.toString();
        this.itemType = itemType;
    }
}
