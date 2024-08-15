package com.dental.doctor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddLabDto {
    private String doctorId;
    private String labId;

}
