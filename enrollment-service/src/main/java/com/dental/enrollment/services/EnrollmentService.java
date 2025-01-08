package com.dental.enrollment.services;

import com.dental.enrollment.dto.*;
import com.dental.enrollment.model.User;
import com.dental.enrollment.repository.EnrollmentRepository;
import com.dental.enrollment.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository userRepository;
    private final RoleRepository roleRepository;
    private final WebClient.Builder webClientBuilder;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUserWithRole(UserRequest userRequest,String roleName) throws Exception {
        try {

        User user = User.builder()
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .role(roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid role name")))
                .build();

        userRepository.save(user);
        if(Objects.equals(roleName, "DOCTOR")) {
            return createDoctor(user, userRequest);
        } else {
            return createLab(user, userRequest);
        }
        }catch (Exception exception){
            throw exception;
        }


    }
    public UserResponse createDoctor(User user,UserRequest userRequest) throws Exception{
        try {
        DoctorRequest doctorRequest = DoctorRequest.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .address(userRequest.getAddress())
                .build();

        ParameterizedTypeReference<ApiResponse<UserResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        Mono<ResponseEntity<ApiResponse<UserResponse>>> responseEntityMono = webClientBuilder.build().post()
                .uri("http://doctor-service/api/doctor")
                .bodyValue(doctorRequest)
                .retrieve()
                .toEntity(responseType);

        ResponseEntity<ApiResponse<UserResponse>> responseEntity = responseEntityMono.block();
        ApiResponse<UserResponse> apiResponse = responseEntity.getBody();
        UserResponse userResponse = apiResponse.getData();

        return userResponse;
        }
        catch (Exception exception){
            throw exception;
        }

    }

    public UserResponse createLab(User user,UserRequest userRequest) throws Exception{
        try {


        LabRequest labRequest = LabRequest.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .labName(userRequest.getLabName())
                .address(userRequest.getAddress())
                .ownerName(userRequest.getOwnerName())
                .labNo(userRequest.getLabNo())
                .build();

        ParameterizedTypeReference<ApiResponse<UserResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        Mono<ResponseEntity<ApiResponse<UserResponse>>> responseEntityMono = webClientBuilder.build().post()
                .uri("http://lab-service/api/lab")
                .bodyValue(labRequest)
                .retrieve()
                .toEntity(responseType);

        ResponseEntity<ApiResponse<UserResponse>> responseEntity = responseEntityMono.block();
        ApiResponse<UserResponse> apiResponse = responseEntity.getBody();
        UserResponse userResponse = apiResponse.getData();

        return userResponse;
        } catch (Exception exception){
            throw exception;
        }

    }
}
