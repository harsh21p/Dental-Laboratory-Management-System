package com.dental.enrollment;

import com.dental.enrollment.model.Role;
import com.dental.enrollment.repository.EnrollmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnrollmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnrollmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(EnrollmentRepository enrollmentRepository) {
		return args -> {
			Role enrollment1 =  Role.builder()
					.roleName("ADMIN")
					.build();

			Role enrollment2 =  Role.builder()
					.roleName("LAB")
					.build();

			Role enrollment3 =  Role.builder()
					.roleName("DOCTOR")
					.build();

			enrollmentRepository.save(enrollment1);
			enrollmentRepository.save(enrollment2);
			enrollmentRepository.save(enrollment3);
		};
	}
}
