package com.dental.doctor;

import com.dental.doctor.model.DbCred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(DbCred.class)
public class DoctorApplication implements CommandLineRunner {

	@Autowired
	private DbCred dbCred;



	public static void main(String[] args) {
		SpringApplication.run(DoctorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
