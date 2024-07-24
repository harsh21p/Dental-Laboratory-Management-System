package com.dental.doctor.controller;

import com.dental.doctor.dto.DoctorRequest;
import com.dental.doctor.dto.DoctorResponse;
import com.dental.doctor.dto.PagedResponse;
import com.dental.doctor.model.Doctor;
import com.dental.doctor.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor createDoctor(@RequestBody DoctorRequest doctorRequest){
        return doctorService.createDoctor(doctorRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse getDoctors(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size){
        return doctorService.getDoctors(page, size);
    }
}
