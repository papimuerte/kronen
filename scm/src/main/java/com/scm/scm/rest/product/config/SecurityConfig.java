package com.scm.scm.rest.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF if needed
                .authorizeExchange(auth -> auth
                        .pathMatchers("/auth/**", "/api/**","/users/**", "/v3/**","/**").permitAll() // Allow public access to auth endpoints
                        .anyExchange().authenticated()
                )
                .build();
    }
}

