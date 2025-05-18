package com.demoApp.order.repository;

import com.demoApp.order.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessRepository extends JpaRepository<Mess, Long> {
} 