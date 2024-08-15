package com.dental.doctor.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("dental")
public class DbCred {
    private String username;
    private String password;
    private String db_url;
}
