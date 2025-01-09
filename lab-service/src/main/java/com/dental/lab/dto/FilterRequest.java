package com.dental.lab.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class FilterRequest {
    private String labId;
    private List<String> doctorId;
    private String doctorIdOne;
    private String materialId;
    private String entryId;
    private String invoiceId;
    private Date startDate;   
    private Date endDate;
    private String name;   
    private Integer page;
    private Integer size;
    private List<String> entries;
}
