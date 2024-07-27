package com.dental.doctor.services;

import com.dental.doctor.dto.DoctorRequest;
import com.dental.doctor.dto.DoctorResponse;
import com.dental.doctor.dto.PagedResponse;
import com.dental.doctor.model.Doctor;
import com.dental.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final WebClient.Builder webClientBuilder;

    public Doctor createDoctor(DoctorRequest doctorRequest) {
        Doctor doctor = Doctor.builder()
                .name(doctorRequest.getName())
                .surname(doctorRequest.getSurname())
                .phone(doctorRequest.getPhone())
                .email(doctorRequest.getEmail())
                .role(doctorRequest.getRole())
                .build();
        doctorRepository.save(doctor);

//        PagedResponse pagedResponse = webClientBuilder.build().get()
//                .uri("http://doctor/api/doctors",
//                        uriBuilder -> uriBuilder.queryParam("name","name")
//                                .build())
//                .retrieve()
//                .bodyToMono(PagedResponse.class)
//                .block();

        return doctor;
    }

    public PagedResponse getDoctors(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);

        List<DoctorResponse> doctorResponses = doctorPage.stream()
                .map(this::convertToRetiredItem)
                .collect(Collectors.toList());

        return new PagedResponse(
                doctorResponses,
                doctorPage.getNumber(),
                doctorPage.getSize(),
                doctorPage.getTotalElements(),
                doctorPage.getTotalPages(),
                doctorPage.isLast()
        );
    }

    private DoctorResponse convertToRetiredItem(Doctor doctor) {
        return DoctorResponse.builder()
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .build();
    }
}
