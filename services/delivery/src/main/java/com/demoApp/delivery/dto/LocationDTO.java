package com.demoApp.delivery.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class LocationDTO {
    private Double latitude;
    private Double longitude;

    // Constructor with public access
    public LocationDTO(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
