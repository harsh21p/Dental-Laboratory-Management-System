package com.dental.doctor.services;

import com.dental.doctor.config.DoctorSpecification;
import com.dental.doctor.dto.*;
import com.dental.doctor.model.Doctor;
import com.dental.doctor.model.Lab;
import com.dental.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final WebClient.Builder webClientBuilder;

    public Doctor createDoctor(DoctorRequest doctorRequest) throws Exception {

        Doctor doctor = Doctor.builder()
                .userId(doctorRequest.getUserId())
                .phone(doctorRequest.getPhone())
                .email(doctorRequest.getEmail())
                .lastName(doctorRequest.getLastName())
                .firstName(doctorRequest.getFirstName())
                .build();

        try {
            doctorRepository.save(doctor);
            return doctor;
        } catch (Exception exception) {
            throw exception;
        }
    }

    public PagedResponse getAllDoctors(int page, int size) throws Exception {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
            return new PagedResponse(
                    doctorPage.getContent(),
                    doctorPage.getNumber(),
                    doctorPage.getSize(),
                    doctorPage.getTotalElements(),
                    doctorPage.getTotalPages(),
                    doctorPage.isLast()
            );
        } catch (Exception exception){
            throw exception;
        }
    }

    public  PagedResponse getDoctorsFilter(int page, int size, String labId) throws Exception {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Doctor> labPage = doctorRepository.findAll(DoctorSpecification.filterByParameters( labId), pageable);
            return new PagedResponse(
                    labPage.getContent(),
                    labPage.getNumber(),
                    labPage.getSize(),
                    labPage.getTotalElements(),
                    labPage.getTotalPages(),
                    labPage.isLast()
            );
        } catch (Exception exception){
            throw exception;
        }
    }


    public Doctor getDoctorById(String doctorId) throws Exception {
        return doctorRepository.findById(doctorId).orElse(null);
    }

    public String removeLabFromDoctorInternal(String doctorId, String labId) {
        try {
            Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
            Lab lab = getLabById(labId);
            if (doctorOptional.isPresent()) {
                Doctor doctor = doctorOptional.get();
                doctor.removeLab(lab);
                doctorRepository.save(doctor);
                return "Lab removed from doctor";
            } else {
                return "Doctor not found";
            }
        } catch (Exception exception) {
            return "Failed to remove lab from doctor " + exception.getMessage();
        }
    }

    private Lab getLabById(String labId) {
        ParameterizedTypeReference<ApiResponse<Lab>> responseType =
                new ParameterizedTypeReference<>() {};

        Mono<ResponseEntity<ApiResponse<Lab>>> responseEntityMono = webClientBuilder.build().get()
                .uri("http://lab-service/api/lab", uriBuilder -> uriBuilder.queryParam("labId", labId).build())
                .retrieve()
                .toEntity(responseType);

        ResponseEntity<ApiResponse<Lab>> responseEntity = responseEntityMono.block();
        ApiResponse<Lab> apiResponse = responseEntity.getBody();
        Lab lab = apiResponse.getData();
        return lab;
    }



}
