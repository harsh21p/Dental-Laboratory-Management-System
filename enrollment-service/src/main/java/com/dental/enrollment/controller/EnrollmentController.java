package com.dental.enrollment.controller;
import com.dental.enrollment.dto.ApiResponse;
import com.dental.enrollment.dto.UserRequest;
import com.dental.enrollment.dto.UserResponse;
import com.dental.enrollment.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/doctor")
    public ResponseEntity<ApiResponse<UserResponse>> createDoctor(@RequestBody UserRequest userRequest) {
        try {
            ApiResponse<UserResponse> response = new ApiResponse<>(200,false, "Data fetched successfully", enrollmentService.createUserWithRole(userRequest, "DOCTOR"));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<UserResponse> response = new ApiResponse<>(500,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/lab")
    public ResponseEntity<ApiResponse<UserResponse>> createLab(@RequestBody UserRequest userRequest) {
        try {
            ApiResponse<UserResponse> response = new ApiResponse<>(200,false, "Data fetched successfully", enrollmentService.createUserWithRole(userRequest, "LAB"));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<UserResponse> response = new ApiResponse<>(500,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
