package com.dental.lab.services;

import com.dental.lab.model.Balance;
import com.dental.lab.repository.DoctorLabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorLabService {
    private final DoctorLabRepository doctorLabRepository;

    public Optional<Balance> findByDoctorIdAndLabId(String doctorId, String labId) {
        Optional<Balance> doctorLab = doctorLabRepository.findByDoctorIdAndLabId(doctorId, labId);
        return doctorLab;
    }

}
