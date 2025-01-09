package com.dental.lab.repository;

import com.dental.lab.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
    Page<Material> findAll(Specification<Material> materialSpecification, Pageable pageable);
}
