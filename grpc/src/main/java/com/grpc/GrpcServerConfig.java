package com.grpc;

import com.grpc.util.InventoryDataUtil;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

/**
 * Configures the gRPC server.
 */
@Configuration
public class GrpcServerConfig {

    /**
     * Provides the inventory data utility.
     */
    @Bean
    public InventoryDataUtil inventoryDataUtil() throws IOException {
        return new InventoryDataUtil();
    }

    /**
     * Registers the inventory service implementation.
     */
    @Bean
    public InventoryServiceImpl inventoryServiceImpl(InventoryDataUtil inventoryDataUtil) {
        return new InventoryServiceImpl(inventoryDataUtil);
    }

    /**
     * Creates and starts the gRPC server.
     */
    @Bean
    public Server grpcServer(InventoryServiceImpl inventoryServiceImpl) throws IOException {
        Server server = ServerBuilder.forPort(9090)
            .addService(inventoryServiceImpl.bindService()) // Register service
            .addService(ProtoReflectionService.newInstance()) // Enable reflection
            .build();

        System.out.println("gRPC server started on port 9090");
        server.start();

        // Graceful shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            server.shutdown();
        }));

        return server;
    }
}
