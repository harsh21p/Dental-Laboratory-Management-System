package com.dental.lab.dto;

import lombok.Data;

@Data
public class ApiResponse<T>  {
    private Integer status;
    private String message;
    private T data;
    private boolean error;

    public ApiResponse(Integer success, Boolean error, String message, T data) {
        this.status = success;
        this.message = message;
        this.data = data;
        this.error = error;
    }
}
