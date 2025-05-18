package com.demoApp.admin.repository;

import com.demoApp.admin.entity.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    @Query("SELECT m FROM SystemMetric m")
    List<Analytics> getServiceMetrics();
}