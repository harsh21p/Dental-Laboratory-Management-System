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

    public DoctorResponse createDoctor(DoctorRequest doctorRequest) throws Exception {
        Doctor doctor = Doctor.builder()
                .userId(doctorRequest.getUserId())
                .phone(doctorRequest.getPhone())
                .email(doctorRequest.getEmail())
                .lastName(doctorRequest.getLastName())
                .firstName(doctorRequest.getFirstName())
                .build();

        try {
            doctorRepository.save(doctor);
            return DoctorResponse.builder()
                    .id(doctor.getId())
                    .firstName(doctor.getFirstName())
                    .lastName(doctor.getLastName())
                    .phone(doctor.getPhone())
                    .email(doctor.getEmail())
                    .build();
        } catch (Exception exception) {
            throw exception;
        }
    }

    public PagedResponse getDoctors(int page, int size) throws Exception{
        try {
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
        }catch (Exception exception){
            throw exception;
        }
    }

    private DoctorResponse convertToRetiredItem(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .lastName(doctor.getLastName())
                .firstName(doctor.getFirstName())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .build();
    }
}
