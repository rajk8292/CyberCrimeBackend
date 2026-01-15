package com.app.cybercrime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.cybercrime.entity.CrimeCase;

public interface CrimeCaseRepository extends JpaRepository<CrimeCase, Long>  {
	

}
