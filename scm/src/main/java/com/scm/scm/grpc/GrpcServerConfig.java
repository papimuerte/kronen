package com.scm.scm.grpc;

import com.scm.scm.util.InventoryDataUtil;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    @Bean
    public InventoryDataUtil inventoryDataUtil() throws IOException {
        return new InventoryDataUtil(); // Properly handle IOException
    }

    @Bean
    public InventoryServiceImpl inventoryServiceImpl(InventoryDataUtil inventoryDataUtil) {
        return new InventoryServiceImpl(inventoryDataUtil);
    }

    @Bean
    public Server garpcServer(InventoryServiceImpl inventoryServiceImpl) throws IOException {
        // Use bindService() to register the service
        Server server = ServerBuilder.forPort(9090)
            .addService(inventoryServiceImpl.bindService()) // Correctly bind the gRPC service
            .addService(ProtoReflectionService.newInstance()) // Enable reflecti
            .build();

        System.out.println("gRPC server started, listening on port 9090");
        server.start();

        // Graceful shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            server.shutdown();
        }));

        return server;
    }
}