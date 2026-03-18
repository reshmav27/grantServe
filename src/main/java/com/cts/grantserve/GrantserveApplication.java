package com.cts.grantserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrantserveApplication {

	public static void main(String[] args) {
		// This is the ONLY line needed to start Spring and Hibernate
		SpringApplication.run(GrantserveApplication.class, args);

		System.out.println("Database tables have been created successfully by Spring Boot!");
	}

}