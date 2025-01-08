package com.dental.lab.controller;

import com.dental.lab.dto.*;
import com.dental.lab.model.Images;
import com.dental.lab.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lab/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse<Images>> addImages(@RequestBody ImagesRequest imagesRequest) {
        try {
            ApiResponse<Images> response = new ApiResponse<>(200,false, "Data fetched successfully", imageService.addImages(imagesRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Images> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Images>> updateImages(@RequestBody ImagesRequest imagesRequest) {
        try {
            ApiResponse<Images> response = new ApiResponse<>(200,false, "Data fetched successfully", imageService.updateImages(imagesRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Images> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Images>> getImages(@RequestParam String id) {
        try {
            ApiResponse<Images> response = new ApiResponse<>(200,false, "Data fetched successfully", imageService.getImages(id));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Images> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteImages(@RequestParam String id) {
        try {
            ApiResponse<String> response = new ApiResponse<>(200,false, "Data fetched successfully", imageService.deleteImages(id));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<String> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
