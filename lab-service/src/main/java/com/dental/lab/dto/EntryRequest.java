package com.dental.lab.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EntryRequest {
    private String labId;
    private String doctorId;
    private String materialId;
    private Date entryDate;
    private Double amount;
}
