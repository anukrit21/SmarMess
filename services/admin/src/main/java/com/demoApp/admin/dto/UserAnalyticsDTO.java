package com.demoApp.admin.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class UserAnalyticsDTO {
    private int totalUsers;
    private int activeUsers;
    private int newUsers; 
    private Map<String, Long> roleDistribution;
    private Map<String, Long> statusDistribution;
}
