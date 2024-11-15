package com.dental.lab.controller;

import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.FilterRequest;
import com.dental.lab.dto.LabMaterialDto;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.LabMaterial;
import com.dental.lab.model.Material;
import com.dental.lab.services.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/lab/material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public ResponseEntity<ApiResponse<Material>> addMaterial(@RequestBody Material material) {
        try {
            ApiResponse<Material> response = new ApiResponse<>(200, false, "Data fetched successfully", materialService.addMaterial(material));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception) {
            ApiResponse<Material> response = new ApiResponse<>(200, true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/add-to-lab")
    public ResponseEntity<ApiResponse<LabMaterial>> addMaterialToLab(@RequestBody LabMaterialDto labMaterialDto) {

        try {
            ApiResponse<LabMaterial> response = new ApiResponse<>(200,false, "Data fetched successfully",materialService.addMaterialToLab(labMaterialDto.getLabId(), labMaterialDto.getMaterialId(), labMaterialDto.getPrice()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<LabMaterial> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/remove-from-lab")
    public ResponseEntity<ApiResponse<String>> removeMaterialFromLab(@RequestParam String id) {
        try {
            ApiResponse<String> response = new ApiResponse<>(200,false, "Data fetched successfully",materialService.removeMaterialFromLab(id));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<String> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/lab-material/filter")
    public ResponseEntity<ApiResponse<PagedResponse<LabMaterial>>> getLabMaterials(@RequestBody FilterRequest request) {
        try {
            ApiResponse<PagedResponse<LabMaterial>> response = new ApiResponse<>(200,false, "Data fetched successfully",materialService.getLabMaterialByLab(request.getPage(),request.getSize(), request.getLabId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<LabMaterial>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }


    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<PagedResponse<Material>>> getMaterials(@RequestBody FilterRequest request) {
        try {
            ApiResponse<PagedResponse<Material>> response = new ApiResponse<>(200,false, "Data fetched successfully",materialService.getMaterial(request.getPage(),request.getSize(), request.getName()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Material>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
