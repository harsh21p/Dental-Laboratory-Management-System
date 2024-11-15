package com.dental.lab.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionRequest {
    private String labId;
    private String doctorId;
    private Double amount;
    private Date date;
    private String reason;
}
