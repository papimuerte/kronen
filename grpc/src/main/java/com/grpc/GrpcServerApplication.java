package com.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

/**
 * Entry point for the gRPC server application.
 */
@SpringBootApplication
public class GrpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcServerApplication.class, args);
    }

    /**
     * Customizes the web server port.
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableReactiveWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setPort(0); // Set port dynamically
    }
}
