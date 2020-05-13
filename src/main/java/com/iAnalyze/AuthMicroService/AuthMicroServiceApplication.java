package com.iAnalyze.AuthMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootApplication
public class AuthMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthMicroServiceApplication.class, args);
	}


}
