package com.dental.lab.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabMaterialDto {
    private String labId;
    private String doctorId;
    private String materialId;
    private Date date;
    private Double price;
}



