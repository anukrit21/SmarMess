package com.demoApp.mess.repository;

import com.demoApp.mess.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessRepository extends JpaRepository<Mess, Long> {
    
    // Check if a mess exists by email
    boolean existsByEmail(String email);
    
    // Find all messes that are approved or not
    List<Mess> findByApproved(boolean approved);
    
    // Find all active messes
    List<Mess> findByActive(boolean active);

    Optional<Mess> findByEmail(String email);
    List<Mess> findByApprovedTrue();
    List<Mess> findByActiveTrue();
}
