package com.dental.lab.controller;

import com.dental.lab.dto.*;
import com.dental.lab.model.Balance;
import com.dental.lab.model.Lab;
import com.dental.lab.services.DoctorLabService;
import com.dental.lab.services.LabService;
import com.dental.lab.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/lab")
@RequiredArgsConstructor
public class LabController {

    private final LabService labService;
    private final TransactionService transactionService;
    private final DoctorLabService doctorLabService;

    @PostMapping
    public ResponseEntity<ApiResponse<Lab>> createLab(@RequestBody LabRequest labRequest) {
        try {
            ApiResponse<Lab> response = new ApiResponse<>(200,false, "Data fetched successfully", labService.createLab(labRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Lab> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Lab>> getLab(@RequestParam String labId) {
        try {
            ApiResponse<Lab> response = new ApiResponse<>(200,false, "Data fetched successfully", labService.getLabById(labId));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Lab> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse<Lab>> getLabByEmail(@RequestParam String email) {
        try {
            ApiResponse<Lab> response = new ApiResponse<>(200,false, "Data fetched successfully", labService.getLabByEmailId(email));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Lab> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }


    @PostMapping("/addDoctor")
    public ResponseEntity<ApiResponse<AddDoctorDto>> addDoctor(@RequestBody AddDoctorDto addDoctorDto) {
        try {
            ApiResponse<AddDoctorDto> response = new ApiResponse<>(200,false, "Data fetched successfully",labService.addDoctorToLab(addDoctorDto.getDoctorId(),addDoctorDto.getLabId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<AddDoctorDto> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/removeDoctor")
    public ResponseEntity<ApiResponse<String>> removeDoctor(@RequestBody AddDoctorDto addDoctorDto) {
        try {
            ApiResponse<String> response = new ApiResponse<>(200,false, "Data fetched successfully",labService.removeDoctorFromLab(addDoctorDto.getDoctorId(),addDoctorDto.getLabId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<String> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/getAllLabs")
    public ResponseEntity<ApiResponse<PagedResponse<Lab>>> getAllLab(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10" ) int size) {
        try {
            ApiResponse<PagedResponse<Lab>> response = new ApiResponse<>(200,false, "Data fetched successfully",labService.getAllLabs(page,size));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Lab>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/get-doctor-by-id")
    public ResponseEntity<ApiResponse<Double>> getAllLab(@RequestBody FilterRequest filterRequest) {
        try {
            ApiResponse<Double> response = new ApiResponse<>(200,false, "Data fetched successfully", getDoctorWithBalance(filterRequest));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception){
            ApiResponse<Double> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    private Double getDoctorWithBalance(FilterRequest filterRequest) throws Exception {
        Optional<Balance> doctorLab = doctorLabService.findByDoctorIdAndLabId(filterRequest.getDoctorId(),filterRequest.getLabId());
        try {
            return doctorLab.get().getBalance();
        } catch (Exception e){
            return 0.0;
        }
    }
}
