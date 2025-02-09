package com.dataservice;

// Main class to bootstrap and run the Spring Boot application.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }
}

