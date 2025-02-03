package com.scm.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // Disable CSRF protection
        http.csrf(csrf -> csrf.disable());

        // Configure public routes
        http.authorizeExchange(exchange -> exchange
                .pathMatchers("/**","/api/", "/auth/**", "/graphql/", "/grpc/", "/v3/", "/swagger-ui.html", "/webjars/").permitAll()
                .anyExchange().authenticated() // All other routes require authentication
        );

        // Enable HTTP Basic Authentication
        http.httpBasic(withDefaults());

        // Build and return the security filter chain
        return http.build();
    }
}

