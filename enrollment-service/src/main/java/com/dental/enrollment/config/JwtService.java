package com.dental.enrollment.config;

import com.dental.enrollment.dto.ApiResponse;
import com.dental.enrollment.dto.Lab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    @Autowired
    private WebClient.Builder webClientBuilder;

    public String getId(String username) {
        try {
            ParameterizedTypeReference<ApiResponse<Lab>> responseType =
                    new ParameterizedTypeReference<>() {};

            Mono<ResponseEntity<ApiResponse<Lab>>> responseEntityMono = webClientBuilder.build().get()
                    .uri("http://lab-service/api/lab/by-email", uriBuilder -> uriBuilder.queryParam("email", username).build())
                    .retrieve()
                    .toEntity(responseType);

            ResponseEntity<ApiResponse<Lab>> responseEntity = responseEntityMono.block();

            ApiResponse<Lab> apiResponse = responseEntity.getBody();

            return apiResponse.getData().getId().toString();
        } catch (Exception exception){
            throw exception;
        }
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}