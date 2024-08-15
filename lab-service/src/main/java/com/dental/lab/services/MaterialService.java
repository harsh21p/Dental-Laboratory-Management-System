package com.dental.lab.services;

import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.Lab;
import com.dental.lab.model.LabMaterial;
import com.dental.lab.model.Material;
import com.dental.lab.repository.LabMaterialRepository;
import com.dental.lab.repository.LabRepository;
import com.dental.lab.repository.MaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final LabRepository labRepository;
    private final LabMaterialRepository labMaterialRepository;

    public Material addMaterial(Material material) {
        Material material1 = Material.builder()
                .name(material.getName())
                .build();
        return materialRepository.save(material1);
    }

    public LabMaterial addMaterialToLab(String labId, String materialId, Double price) {
        Optional<Lab> labOpt = labRepository.findById(labId);
        Optional<Material> materialOpt = materialRepository.findById(materialId);

        if (labOpt.isPresent() && materialOpt.isPresent()) {
            Lab lab = labOpt.get();
            Material material = materialOpt.get();

            LabMaterial labMaterial = new LabMaterial();
            LabMaterial.LabMaterialId labMaterialId = new LabMaterial.LabMaterialId();
            labMaterialId.setLabId(labId);
            labMaterialId.setMaterialId(materialId);
            labMaterial.setId(labMaterialId);
            labMaterial.setLab(lab);
            labMaterial.setMaterial(material);
            labMaterial.setPrice(price);

            return labMaterialRepository.save(labMaterial);

        } else {
            throw new IllegalArgumentException("Lab or Material not found");
        }
    }

    public String removeMaterialFromLab(String labId, String materialId) {
        LabMaterial.LabMaterialId labMaterialId = new LabMaterial.LabMaterialId();
        labMaterialId.setLabId(labId);
        labMaterialId.setMaterialId(materialId);

        Optional<LabMaterial> labMaterialOpt = labMaterialRepository.findById(labMaterialId);
        if (labMaterialOpt.isPresent()) {
            labMaterialRepository.delete(labMaterialOpt.get());
            return "Lab material deleted";
        } else {
            throw new IllegalArgumentException("Association not found");
        }
    }

    public PagedResponse<Material> getAllMaterial(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Material> materialPage = materialRepository.findAll(pageable);

            return new PagedResponse<Material>(
                    materialPage.getContent(),
                    materialPage.getNumber(),
                    materialPage.getSize(),
                    materialPage.getTotalElements(),
                    materialPage.getTotalPages(),
                    materialPage.isLast()
            );
        }catch (Exception exception){
            throw exception;
        }
    }
}
