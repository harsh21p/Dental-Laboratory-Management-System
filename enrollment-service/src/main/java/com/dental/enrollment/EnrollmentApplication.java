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
			String[] roles = {"ADMIN", "LAB", "DOCTOR"};

			for (String roleName : roles) {
				if (!roleRepository.existsByName(roleName)) {
					Role role = Role.builder()
							.name(roleName)
							.build();
					roleRepository.save(role);
				}
			}
		};
	}
}
