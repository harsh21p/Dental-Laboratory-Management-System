package com.dental.lab.repository;

import com.dental.lab.model.LabMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabMaterialRepository extends JpaRepository<LabMaterial, LabMaterial.LabMaterialId> {
}
