package com.hms.hospital.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hms.hospital.entity.Role;
import com.hms.hospital.entity.User;
import com.hms.hospital.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {

		if (userRepository.findByEmail("admin@hospital.com").isEmpty()) {

			User admin = new User();

			admin.setName("Hospital Admin");
			admin.setEmail("admin@hospital.com");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);

			userRepository.save(admin);
		}

		if (userRepository.findByEmail("madhav@hospital.com").isEmpty()) {

			User doctor1 = new User();

			doctor1.setName("Dr. Madhav Gonge");
			doctor1.setEmail("madhav@hospital.com");
			doctor1.setPassword(passwordEncoder.encode("doctor123"));
			doctor1.setRole(Role.DOCTOR);

			userRepository.save(doctor1);
		}

		if (userRepository.findByEmail("sandhya@hospital.com").isEmpty()) {

			User doctor2 = new User();

			doctor2.setName("Dr. Sandhya Gonge");
			doctor2.setEmail("sandhya@hospital.com");
			doctor2.setPassword(passwordEncoder.encode("doctor123"));
			doctor2.setRole(Role.DOCTOR);

			userRepository.save(doctor2);
		}
	}
}