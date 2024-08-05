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
    private String firstName;
    private String email;
    private String phone;
}
