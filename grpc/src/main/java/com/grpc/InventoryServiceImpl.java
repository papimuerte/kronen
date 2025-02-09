package com.grpc;

import com.grpc.InventoryServiceGrpc.InventoryServiceImplBase;
import com.grpc.InventoryServiceOuterClass.InventoryUpdateResponse;
import com.grpc.InventoryServiceOuterClass.ProductRequest;
import com.grpc.InventoryServiceOuterClass.ProductResponse;
import com.grpc.InventoryServiceOuterClass.UpdateInventoryRequest;
import com.grpc.model.Product;
import com.grpc.util.InventoryDataUtil;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.Optional;

/**
 * Implements the gRPC service for inventory management.
 */
@GrpcService
public class InventoryServiceImpl extends InventoryServiceImplBase {

    private final InventoryDataUtil inventoryDataUtil;

    public InventoryServiceImpl(InventoryDataUtil inventoryDataUtil) {
        this.inventoryDataUtil = inventoryDataUtil;
    }

    /**
     * Checks if a product is available in stock.
     */
    @Override
    public void checkAvailability(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        try {
            Optional<Product> productOptional = inventoryDataUtil.getProductById(request.getProductId());

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                boolean isAvailable = product.getAvailableQuantity() >= request.getQuantity();

                ProductResponse response = ProductResponse.newBuilder()
                        .setProductId(product.getProductId())
                        .setAvailable(isAvailable)
                        .setAvailableQuantity(product.getAvailableQuantity())
                        .build();

                System.out.println("Sending Response: " + response);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("Product not found: " + request.getProductId())
                        .asRuntimeException());
            }
        } catch (Exception e) {
            responseObserver.onError(Status.UNKNOWN
                    .withDescription("Error occurred")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    /**
     * Updates the inventory for a product.
     */
    @Override
    public void updateInventory(UpdateInventoryRequest request, StreamObserver<InventoryUpdateResponse> responseObserver) {
        try {
            Optional<Product> productOptional = inventoryDataUtil.getProductById(request.getProductId());

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                if (product.getAvailableQuantity() >= request.getQuantity()) {
                    product.setAvailableQuantity(product.getAvailableQuantity() - request.getQuantity()); // Reduce stock
                    inventoryDataUtil.updateProduct(product); // Update inventory

                    InventoryUpdateResponse response = InventoryUpdateResponse.newBuilder()
                            .setProductId(product.getProductId())
                            .setSuccess(true)
                            .setMessage("Inventory updated successfully")
                            .build();

                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } else {
                    InventoryUpdateResponse response = InventoryUpdateResponse.newBuilder()
                            .setProductId(product.getProductId())
                            .setSuccess(false)
                            .setMessage("Insufficient inventory")
                            .build();

                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                }
            } else {
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("Product not found: " + request.getProductId())
                        .asRuntimeException());
            }
        } catch (Exception e) {
            responseObserver.onError(Status.UNKNOWN
                    .withDescription("Unknown error occurred")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
