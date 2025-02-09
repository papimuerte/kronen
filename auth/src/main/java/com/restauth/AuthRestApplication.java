package com.restauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Main class for the Spring Boot application.
 */
@SpringBootApplication
public class AuthRestApplication {

    /**
     * Main method to launch the Spring Boot application.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthRestApplication.class, args);
    }

    /**
     * Bean to customize the reactive web server factory.
     * This sets the server to listen on port 8084.
     * 
     * @return WebServerFactoryCustomizer instance with port configuration
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableReactiveWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setPort(8084);  // Manually set the port
    }
}