package com.dental.enrollment.controller;

import com.dental.enrollment.config.AuthService;
import com.dental.enrollment.dto.ApiResponse;
import com.dental.enrollment.dto.AuthRequest;
import com.dental.enrollment.dto.AuthResponse;
import com.dental.enrollment.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/enrollment/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<AuthResponse>> getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            try {
                ApiResponse<AuthResponse> response = new ApiResponse<>(200,false, "Data fetched successfully",  service.generateToken(authRequest.getEmail()));
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (Exception exception){
                ApiResponse<AuthResponse> response = new ApiResponse<>(401,true, "Failed to fetch data: " + exception.getMessage(), null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestBody Token token) {
        try {
            ApiResponse<String> response = new ApiResponse<>(200,false, "Data fetched successfully", service.validateToken(token));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception exception){
            ApiResponse<String> response = new ApiResponse<>(401,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}