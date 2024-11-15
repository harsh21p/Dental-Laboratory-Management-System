package com.dental.doctor.repository;

import com.dental.doctor.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,String> {
    Page<Doctor> findAll(Specification<Doctor> doctorSpecification, Pageable pageable);
}
