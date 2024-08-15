package com.dental.lab.services;

import com.dental.lab.dto.AddDoctorDto;
import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.LabRequest;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.Doctor;
import com.dental.lab.model.Lab;
import com.dental.lab.model.Material;
import com.dental.lab.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LabService {

    private final LabRepository labRepository;
    private final WebClient.Builder webClientBuilder;

    public Lab createLab(LabRequest labRequest) throws Exception {
        try {
            Lab lab =  Lab.builder()
                    .name(labRequest.getFirstName())
                    .phone(labRequest.getPhone())
                    .email(labRequest.getEmail())
                    .userId(labRequest.getUserId())
                    .build();
            labRepository.save(lab);
            return lab;
        } catch (Exception exception) {
            throw exception;
        }
    }

    public Lab getLabById(String labId) throws Exception {
       Optional<Lab> labOptional = labRepository.findById(labId);
       if(labOptional.isPresent()) {
           Lab lab = labOptional.get();
            return lab;
       } else {
           throw new Exception("Lab not found");
       }
    }

    public String addDoctorToLab(String doctorId, String labId) throws Exception{
        try {
            Optional<Lab> labOptional = labRepository.findById(labId);
            Doctor userResponse = getDoctorById(doctorId);
            Lab lab = labOptional.get();
            lab.addDoctor(userResponse);
            labRepository.save(lab);
            return "Doctor added to lab";
        } catch (Exception exception){
            return "Failed to add doctor to lab "+exception.getMessage();
        }

    }

   public String removeDoctorFromLab(String doctorId, String labId) {
       try {
           Optional<Lab> labOptional = labRepository.findById(labId);
           ParameterizedTypeReference<ApiResponse<String>> responseType1 =
                   new ParameterizedTypeReference<>() {};
           Mono<ResponseEntity<ApiResponse<String>>> responseEntityMono1 = webClientBuilder.build().post()
                   .uri("http://doctor-service/api/doctor/removeLabInternal")
                   .bodyValue(AddDoctorDto.builder().doctorId(doctorId).labId(labId).build())
                   .retrieve()
                   .toEntity(responseType1);
           ResponseEntity<ApiResponse<String>> responseEntity1 = responseEntityMono1.block();
           ApiResponse<String> apiResponse1 = responseEntity1.getBody();
           String string = apiResponse1.getData();

           Doctor doctor = getDoctorById(doctorId);

           if (labOptional.isPresent()) {
               Lab lab = labOptional.get();
               lab.removeDoctor(doctor);
               labRepository.save(lab);
               return "Doctor removed from lab";
           } else {
               return "Lab not found";
           }
       } catch (Exception exception) {
           System.out.println("Failed to remove doctor from lab"+ exception);
           return "Failed to remove doctor from lab " + exception.getMessage();
       }
    }

    public Doctor getDoctorById(String doctorId) throws Exception {
        try {
            ParameterizedTypeReference<ApiResponse<Doctor>> responseType =
                    new ParameterizedTypeReference<>() {};

            Mono<ResponseEntity<ApiResponse<Doctor>>> responseEntityMono = webClientBuilder.build().get()
                    .uri("http://doctor-service/api/doctor", uriBuilder -> uriBuilder.queryParam("doctorId", doctorId).build())
                    .retrieve()
                    .toEntity(responseType);

            ResponseEntity<ApiResponse<Doctor>> responseEntity = responseEntityMono.block();
            ApiResponse<Doctor> apiResponse = responseEntity.getBody();
            Doctor doctor = apiResponse.getData();
            return doctor;
        } catch (Exception exception){
            throw exception;
        }

    }

    public PagedResponse<Lab> getAllLabs(int page, int size) throws Exception {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Lab> labPage = labRepository.findAll(pageable);
            return new PagedResponse<Lab>(
                    labPage.getContent(),
                    labPage.getNumber(),
                    labPage.getSize(),
                    labPage.getTotalElements(),
                    labPage.getTotalPages(),
                    labPage.isLast()
            );
        }catch (Exception exception){
            throw exception;
        }
    }
}
