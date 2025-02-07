package com.scm.scm.rest.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.server.ConfigurableReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthRestApplication.class, args);
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableReactiveWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setPort(8084);  // Manually set the port
    }
}
