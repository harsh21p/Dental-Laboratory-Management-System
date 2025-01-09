package com.dental.lab.repository;

import com.dental.lab.model.Invoice;
import com.dental.lab.model.LabMaterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabMaterialRepository extends JpaRepository<LabMaterial, String> {
    Page<LabMaterial> findAll(Specification<LabMaterial> materialSpecification, Pageable pageable);
}
