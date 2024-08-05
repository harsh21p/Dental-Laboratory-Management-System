package com.dental.enrollment;

import com.dental.enrollment.model.Role;
import com.dental.enrollment.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

//import static com.dental.enrollment.services.JwtService.generateSecretKey;

@SpringBootApplication
@EnableDiscoveryClient
public class EnrollmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnrollmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(RoleRepository roleRepository) {
		return args -> {
			Role enrollment1 =  Role.builder()
					.name("ADMIN")
					.build();

			Role enrollment2 =  Role.builder()
					.name("LAB")
					.build();

			Role enrollment3 =  Role.builder()
					.name("DOCTOR")
					.build();

			roleRepository.save(enrollment1);
			roleRepository.save(enrollment2);
			roleRepository.save(enrollment3);
		};
	}
}
