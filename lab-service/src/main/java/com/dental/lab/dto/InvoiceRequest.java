package com.dental.lab.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class InvoiceRequest {
    private List<String> entryIds;
    private Date invoiceDate;
    private String labId;
    private String doctorId;
}
