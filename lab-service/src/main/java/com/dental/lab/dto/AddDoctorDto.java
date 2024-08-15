package com.dental.lab.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddDoctorDto {
    private String labId;
    private String doctorId;
}
