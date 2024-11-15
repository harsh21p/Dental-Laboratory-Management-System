package com.dental.doctor.controller;

import com.dental.doctor.dto.*;
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
    public ResponseEntity<ApiResponse<Doctor>> createDoctor(@RequestBody DoctorRequest doctorRequest) {
        try {
            ApiResponse<Doctor> response = new ApiResponse<>(200,false, "Data fetched successfully", doctorService.createDoctor(doctorRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Doctor> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }


    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<PagedResponse>> createDoctor(@RequestBody FilterRequest filterRequest) {
        try {
            ApiResponse<PagedResponse> response = new ApiResponse<>(200,false, "Data fetched successfully", doctorService.getDoctorsFilter(filterRequest.getPage(),filterRequest.getSize(),filterRequest.getLabId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Doctor>> getDoctor(@RequestParam String doctorId) {
        try {
            ApiResponse<Doctor> response = new ApiResponse<>(200,false, "Data fetched successfully", doctorService.getDoctorById(doctorId));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Doctor> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/removeLabInternal")
    public ResponseEntity<ApiResponse<String>> removeLabInternal(@RequestBody AddLabDto addLabDto) {
        try {
            ApiResponse<String> response = new ApiResponse<>(200,false, "Data fetched successfully",doctorService.removeLabFromDoctorInternal(addLabDto.getDoctorId(),addLabDto.getLabId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<String> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/getAllDoctors")
    public ResponseEntity<ApiResponse<PagedResponse>> getAllDoctors(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size) {
        try {
            ApiResponse<PagedResponse> response = new ApiResponse<>(200,false, "Data fetched successfully",doctorService.getAllDoctors(page,size));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
