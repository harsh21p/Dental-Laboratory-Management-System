package com.dental.lab.repository;

import com.dental.lab.model.Balance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorLabRepository extends JpaRepository<Balance, String> {
    Page<Balance> findAll(Specification<Balance> doctorLabSpecification, Pageable pageable);
    Optional<Balance> findByDoctorIdAndLabId(String doctorId, String labId);
}
