package com.dental.enrollment.repository;

import com.dental.enrollment.model.Role;
import com.dental.enrollment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String username);
}
