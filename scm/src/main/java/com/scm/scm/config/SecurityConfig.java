
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
        http.csrf(csrf -> csrf.disable()); // Disable CSRF

        http.authorizeExchange(exchange -> exchange
                .pathMatchers("/api/products/**", "/auth/register", "/auth/login","/graphql/**", "/grpc/**").permitAll() // Public routes
        );

        http.httpBasic(withDefaults()); // Enable HTTP Basic Authentication

        return http.build();
    }
}
