package com.dental.enrollment.dto;

import com.dental.enrollment.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String email;
    private String phone;
    private String password;
    private String labName;
    private String labNo;
    private String address;
    private String ownerName;
    private String firstName;
    private String lastName;
}
