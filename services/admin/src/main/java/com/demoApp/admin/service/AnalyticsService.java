package com.demoApp.admin.service;

import com.demoApp.admin.entity.Analytics;
import com.demoApp.admin.repository.AnalyticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnalyticsService {
    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Transactional(readOnly = true)
    public List<Analytics> getServiceMetrics() {
        return analyticsRepository.getServiceMetrics();
    }
}