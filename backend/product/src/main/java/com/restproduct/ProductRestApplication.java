package com.restproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Main class for the Spring Boot application handling product-related services.
 */
@SpringBootApplication
public class ProductRestApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductRestApplication.class, args);
    }

    /**
     * Customizes the reactive web server to use port 8085.
     * 
     * @return WebServerFactoryCustomizer instance with port configuration
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableReactiveWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setPort(8085);  // Manually set the port
    }
}
