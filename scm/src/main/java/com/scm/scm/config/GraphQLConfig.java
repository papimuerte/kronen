package com.scm.scm.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scm.scm.graphql.OrderInput;
import com.scm.scm.graphql.OrderResolver;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(OrderResolver orderResolver) {
        return wiringBuilder -> wiringBuilder
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("orders", env -> {
                            String customerUsername = env.getArgument("customerUsername");
                            return orderResolver.ordersByCustomer(customerUsername);
                        })
                        .dataFetcher("order", env -> {
                            String id = env.getArgument("id");
                            return orderResolver.order(id);
                        }))
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher("createOrder", env -> {
                            // Convert the raw input to OrderInput
                            ObjectMapper mapper = new ObjectMapper();
                            OrderInput input = mapper.convertValue(env.getArgument("input"), OrderInput.class);
                            return orderResolver.createOrder(input);
                        }));
    }
}

