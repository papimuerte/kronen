package com.restproduct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security configuration for the application using Spring Security and WebFlux.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Configures security rules for handling authentication and authorization.
     * 
     * @param http ServerHttpSecurity configuration
     * @return SecurityWebFilterChain instance
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF if needed for API endpoints
                .authorizeExchange(auth -> auth
                        .pathMatchers("/auth/**", "/api/**", "/api/products/**", "/api/products/admin/**", "/**")
                        .permitAll() // Allow public access to specified endpoints
                        .anyExchange().authenticated() // Require authentication for all other requests
                )
                .build();
    }
}
