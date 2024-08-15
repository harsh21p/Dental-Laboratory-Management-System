package com.dental.lab.controller;

import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.LabMaterial;
import com.dental.lab.model.Material;
import com.dental.lab.services.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<LabMaterial>> addMaterialToLab(@RequestParam String labId, @RequestParam String materialId, @RequestParam Double price) {

        try {
            ApiResponse<LabMaterial> response = new ApiResponse<>(200,false, "Data fetched successfully",materialService.addMaterialToLab(labId, materialId, price));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<LabMaterial> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/remove-from-lab")
    public ResponseEntity<ApiResponse<String>> removeMaterialFromLab(@RequestParam String labId, @RequestParam String materialId) {
        try {
            ApiResponse<String> response = new ApiResponse<>(200,false, "Data fetched successfully",materialService.removeMaterialFromLab(labId, materialId));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<String> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/getAllMaterials")
    public ResponseEntity<ApiResponse<PagedResponse<Material>>> getAllMaterials(@RequestParam(required = false,defaultValue = "0") Integer page, @RequestParam(required = false,defaultValue = "10") Integer size) {
        try {
            ApiResponse<PagedResponse<Material>> response = new ApiResponse<>(200,false, "Data fetched successfully",materialService.getAllMaterial(page,size));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Material>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
