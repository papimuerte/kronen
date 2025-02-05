// package com.scm.scm.grpc;

// import com.scm.scm.grpc.InventoryServiceGrpc.InventoryServiceImplBase;
// import com.scm.scm.grpc.InventoryServiceOuterClass.InventoryUpdateResponse;
// import com.scm.scm.grpc.InventoryServiceOuterClass.ProductRequest;
// import com.scm.scm.grpc.InventoryServiceOuterClass.ProductResponse;
// import com.scm.scm.grpc.InventoryServiceOuterClass.UpdateInventoryRequest;
// import com.scm.scm.model.Product;
// import com.scm.scm.util.InventoryDataUtil;

// import io.grpc.ManagedChannel;
// import io.grpc.Status;
// import io.grpc.stub.StreamObserver;
// import net.devh.boot.grpc.server.service.GrpcService;

// import java.io.IOException;
// import java.util.List;
// import java.util.Optional;

// @GrpcService
// public class InventoryServiceImpl extends InventoryServiceGrpc.InventoryServiceImplBase {

//     private final InventoryDataUtil inventoryDataUtil;

//     public InventoryServiceImpl(InventoryDataUtil inventoryDataUtil) {
//         this.inventoryDataUtil = inventoryDataUtil;
//     }

//     @Override
//     public void checkAvailability(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
//         try {
//             Optional<Product> productOptional = inventoryDataUtil.getProductById(request.getProductId());
//             if (productOptional.isPresent()) {
//                 Product product = productOptional.get();
//                 boolean isAvailable = product.getAvailableQuantity() >= request.getQuantity();
    
//                 ProductResponse response = ProductResponse.newBuilder()
//                         .setProductId(product.getProductId())
//                         .setAvailable(isAvailable)
//                         .setAvailableQuantity(product.getAvailableQuantity())
//                         .build();
    
//                 System.out.println("Sending Response: " + response);
//                 responseObserver.onNext(response);
//                 responseObserver.onCompleted();
//             } else {
//                 responseObserver.onError(Status.NOT_FOUND
//                         .withDescription("Product not found: " + request.getProductId())
//                         .asRuntimeException());
//             }
//         } catch (Exception e) {
//             responseObserver.onError(Status.UNKNOWN
//                     .withDescription("Error occurred")
//                     .withCause(e)
//                     .asRuntimeException());
//         }
//     }
    
    

//     @Override
//     public void updateInventory(UpdateInventoryRequest request, StreamObserver<InventoryUpdateResponse> responseObserver) {
//         try {
//             // Load all products
//             List<Product> allProducts = inventoryDataUtil.loadProducts();
//             Optional<Product> productOptional = allProducts.stream()
//                     .filter(product -> product.getProductId().equals(request.getProductId()))
//                     .findFirst();

//             if (productOptional.isPresent()) {
//                 Product product = productOptional.get();

//                 if (product.getAvailableQuantity() >= request.getQuantity()) {
//                     // Update product quantity
//                     product.setAvailableQuantity(product.getAvailableQuantity() - request.getQuantity());

//                     // Save updated products back to the JSON file
//                     inventoryDataUtil.saveProducts(allProducts);

//                     InventoryUpdateResponse response = InventoryUpdateResponse.newBuilder()
//                             .setProductId(product.getProductId())
//                             .setSuccess(true)
//                             .setMessage("Inventory updated successfully")
//                             .build();

//                     System.out.println("Returning gRPC response: " + response);



//                     responseObserver.onNext(response);
//                     responseObserver.onCompleted();
//                 } else {
//                     InventoryUpdateResponse response = InventoryUpdateResponse.newBuilder()
//                             .setProductId(product.getProductId())
//                             .setSuccess(false)
//                             .setMessage("Insufficient inventory")
//                             .build();

//                     responseObserver.onNext(response);
//                     responseObserver.onCompleted();
//                 }
//             } else {
//                 responseObserver.onError(Status.NOT_FOUND
//                         .withDescription("Product not found: " + request.getProductId())
//                         .asRuntimeException());
//             }
//         } catch (IOException e) {
//             System.err.println("Error during updateInventory: " + e.getMessage());
//             e.printStackTrace();
//             responseObserver.onError(Status.UNKNOWN
//                     .withDescription("Error accessing inventory data")
//                     .withCause(e)
//                     .asRuntimeException());
//         } catch (Exception e) {
//             System.err.println("Error during updateInventory: " + e.getMessage());
//             e.printStackTrace();
//             responseObserver.onError(Status.UNKNOWN
//                     .withDescription("Unknown error occurred")
//                     .withCause(e)
//                     .asRuntimeException());
//         }
//     }

// }
