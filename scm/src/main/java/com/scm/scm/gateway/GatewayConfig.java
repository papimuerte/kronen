package com.scm.scm.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route to REST API
                .route("rest-api", r -> r.path("/api/products/")
                        .uri("http://localhost:8081")) // REST service URL

                // Route to GraphQL API
                .route("graphql-api", r -> r.path("/graphql/")
                        .uri("http://localhost:8082")) // GraphQL service URL

                // Route to gRPC services
                .route("grpc-api", r -> r.path("/grpc/")
                        .uri("http://localhost:8083")) // gRPC service URL

                // Route to Login
                .route("auth-login", r -> r.path("/auth/")
                        .uri("http://localhost:8084")) // Login service URL

                // Route to Registration
                .route("auth-register", r -> r.path("/auth/register")
                        .uri("http://localhost:8085")) // Registration service URL

                .build();
    }
}
