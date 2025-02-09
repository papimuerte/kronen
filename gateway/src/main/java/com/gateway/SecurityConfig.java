package com.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configures security settings for the API gateway.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Defines security rules for API endpoints.
     */
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for API calls
            .cors(cors -> cors.disable())  // Disable CORS restrictions
            .authorizeExchange(auth -> auth
                .pathMatchers("/graphql").permitAll()
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/api/products/**").permitAll()
                .pathMatchers("/users").permitAll()
                .pathMatchers("/v3/**").permitAll()
                .pathMatchers("/webjars/**").permitAll()
                .pathMatchers("/**").permitAll()
                .anyExchange().authenticated()
            )
            .build();
    }
}
