package com.dental.enrollment.dto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lab {
    private String id;
    private String userId;
    private String email;
    private String phone;
}


//enrollment-service
//lab-service
