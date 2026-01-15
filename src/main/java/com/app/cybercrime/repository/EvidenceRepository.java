package com.app.cybercrime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.cybercrime.entity.Evidence;

public interface EvidenceRepository extends JpaRepository<Evidence, Long> {
    List<Evidence> findByCaseId(Long caseId); 

}
