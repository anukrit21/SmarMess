package com.demoApp.subscription.repository;

import com.demoApp.subscription.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByIsActiveTrue();
    boolean existsByName(String name);
} 