package com.app.cybercrime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.cybercrime.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
