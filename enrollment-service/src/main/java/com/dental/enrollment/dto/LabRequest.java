package com.dental.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabRequest {
    private String userId;
    private String email;
    private String phone;
    private String firstName;
}


