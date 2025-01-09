package com.dental.lab.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabRequest {
    private String userId;
    private String labName;
    private String ownerName;
    private String labNo;
    private String email;
    private String address;
    private String phone;
}
