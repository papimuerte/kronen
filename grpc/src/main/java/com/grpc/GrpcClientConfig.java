package com.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the gRPC client for communication with the inventory service.
 */
@Configuration
public class GrpcClientConfig {

    /**
     * Creates a gRPC channel to connect to the server.
     */
    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext() // Disable TLS for local development
                .build();
    }

    /**
     * Creates a blocking stub for the inventory service.
     */
    @Bean
    public InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceStub(ManagedChannel channel) {
        return InventoryServiceGrpc.newBlockingStub(channel);
    }
}
