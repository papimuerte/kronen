package com.restauth;

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
     * Defines the security filter chain for handling authentication and authorization.
     * 
     * @param http ServerHttpSecurity configuration
     * @return SecurityWebFilterChain instance
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for API calls
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth/**").permitAll()  // Allow unauthenticated access to auth endpoints
                        .pathMatchers("/v3/**").permitAll()  // Allow access to API documentation
                        .pathMatchers("/**").permitAll()  // Allow unrestricted access to all other endpoints
                        .pathMatchers("/webjars/**").permitAll()  // Allow access to static web resources
                        .anyExchange().authenticated()  // Require authentication for any other request
                )
                .build();
    }
}
