package com.demoApp.admin.repository;

import com.demoApp.admin.entity.SystemMetric;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SystemMetricRepository extends JpaRepository<SystemMetric, Long> {
    
    Page<SystemMetric> findByServiceName(String serviceName, Pageable pageable);
    
    Page<SystemMetric> findByMetricType(SystemMetric.MetricType metricType, Pageable pageable);
    
    Page<SystemMetric> findByServiceNameAndMetricType(
            String serviceName, SystemMetric.MetricType metricType, Pageable pageable);
    
    Page<SystemMetric> findByTimestampBetween(
            LocalDateTime start, LocalDateTime end, Pageable pageable);
    
    @Query("SELECT sm FROM SystemMetric sm WHERE sm.serviceName = :serviceName AND sm.metricType = :metricType " +
           "AND sm.timestamp = (SELECT MAX(sm2.timestamp) FROM SystemMetric sm2 WHERE sm2.serviceName = :serviceName " +
           "AND sm2.metricType = :metricType)")
    SystemMetric findLatestMetricByServiceAndType(
            @Param("serviceName") String serviceName, 
            @Param("metricType") SystemMetric.MetricType metricType);

    @Query("SELECT sm FROM SystemMetric sm WHERE sm.serviceName = :serviceName " +
           "AND sm.timestamp IN (SELECT MAX(sm2.timestamp) FROM SystemMetric sm2 " +
           "WHERE sm2.serviceName = :serviceName GROUP BY sm2.metricType)")
    List<SystemMetric> findLatestMetricsForService(@Param("serviceName") String serviceName);
} 