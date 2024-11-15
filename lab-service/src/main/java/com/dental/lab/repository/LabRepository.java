package com.dental.lab.repository;

import com.dental.lab.model.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabRepository extends JpaRepository<Lab, String> {
    Optional<Lab> findByEmail(String email);
}
