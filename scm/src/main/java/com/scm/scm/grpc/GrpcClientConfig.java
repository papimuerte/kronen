package com.scm.scm.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext() // Disable TLS for local development
                .build();
    }


    @Bean
    public InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceStub(ManagedChannel channel) {
        return InventoryServiceGrpc.newBlockingStub(channel);
    }
}