package com.dental.doctor.controller;

import com.dental.doctor.dto.ApiResponse;
import com.dental.doctor.dto.DoctorRequest;
import com.dental.doctor.dto.DoctorResponse;
import com.dental.doctor.dto.PagedResponse;
import com.dental.doctor.model.Doctor;
import com.dental.doctor.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> createDoctor(@RequestBody DoctorRequest doctorRequest) {
        try {
            ApiResponse<DoctorResponse> response = new ApiResponse<>(200,false, "Data fetched successfully", doctorService.createDoctor(doctorRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<DoctorResponse> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse>> getDoctors(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size){
        try {
            ApiResponse<PagedResponse> response = new ApiResponse<>(200,false, "Data fetched successfully", doctorService.getDoctors(page, size));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
