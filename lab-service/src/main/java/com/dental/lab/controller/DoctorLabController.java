package com.dental.lab.controller;

import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.FilterRequest;
import com.dental.lab.model.Balance;
import com.dental.lab.services.DoctorLabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/lab/balance")
@RequiredArgsConstructor
public class DoctorLabController {

    private final DoctorLabService doctorLabService;

    @PostMapping
    public ResponseEntity<ApiResponse<Balance>> getBalance(@RequestBody FilterRequest filterRequest) {
        try {
            Optional<Balance> doctorLab = doctorLabService.findByDoctorIdAndLabId(filterRequest.getDoctorId(), filterRequest.getLabId());
            if(doctorLab.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body( new ApiResponse<>(200, false, "Data fetched successfully", doctorLab.get()));
            } else {
                throw new Exception("Doctor not found");
            }

        } catch (Exception exception){
            ApiResponse<Balance> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}