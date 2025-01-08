package com.dental.lab.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagesRequest {
    private String id;
    private String signatureImage;
    private String logoImage;
    private String labId;
}
