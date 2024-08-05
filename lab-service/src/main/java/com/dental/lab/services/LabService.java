package com.dental.lab.services;

import com.dental.lab.dto.LabRequest;
import com.dental.lab.dto.LabResponse;
import com.dental.lab.model.Lab;
import com.dental.lab.repository.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LabService {

    private final LabRepository labRepository;

    public LabResponse createLab(LabRequest labRequest) throws Exception{
        try {

        Lab lab =  Lab.builder()
                .name(labRequest.getFirstName())
                .phone(labRequest.getPhone())
                .email(labRequest.getEmail())
                .userId(labRequest.getUserId())
                .build();

        labRepository.save(lab);

        return LabResponse.builder()
                .id(lab.getId())
                .name(lab.getName())
                .email(lab.getEmail())
                .phone(lab.getPhone())
                .build();
        }catch (Exception exception) {
            throw exception;
        }

    }
}
