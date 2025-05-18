package com.demoApp.campus_module.repository;

import com.demoApp.campus_module.entity.CampusEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampusEventRepository extends JpaRepository<CampusEvent, Long> {
    List<CampusEvent> findByCampusIdAndStartTimeBetween(Long campusId, LocalDateTime startDate, LocalDateTime endDate);
} 