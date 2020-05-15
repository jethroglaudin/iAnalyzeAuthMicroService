package com.iAnalyze.AuthMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootApplication
@EnableEurekaClient
public class AuthMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthMicroServiceApplication.class, args);
    }


}
