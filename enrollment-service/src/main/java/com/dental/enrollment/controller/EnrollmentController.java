package com.dental.enrollment.controller;

import com.dental.enrollment.dto.UserRequest;
import com.dental.enrollment.dto.UserResponse;
import com.dental.enrollment.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserRequest userRequest) {
        return enrollmentService.createUser(userRequest);
    }

}
