package com.app.cybercrime.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.cybercrime.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByTrackingId(String trackingId);
}
