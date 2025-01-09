package com.dental.enrollment.config;

import com.dental.enrollment.dto.AuthResponse;
import com.dental.enrollment.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    public AuthResponse generateToken(String username) {

        return AuthResponse.builder()
                .token(jwtService.generateToken(username))
                .id(jwtService.getId(username))
                .build();
    }

    public String validateToken(Token token) throws Exception {
        try {
            jwtService.validateToken(token.getToken());
            return "Valid Token!";
        } catch (Exception exception){
            throw exception;
        }
    }

}