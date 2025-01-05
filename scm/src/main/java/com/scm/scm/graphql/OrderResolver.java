package com.scm.scm.graphql;

import com.scm.scm.grpc.InventoryServiceGrpc;
import com.scm.scm.grpc.InventoryServiceOuterClass;
import com.scm.scm.grpc.InventoryServiceOuterClass.ProductRequest;
import com.scm.scm.grpc.InventoryServiceOuterClass.ProductResponse;
import com.scm.scm.model.Order;
import com.scm.scm.model.OrderProduct;
import com.scm.scm.util.OrderDataUtil;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderResolver {

    private final OrderDataUtil orderDataUtil;
    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

    public OrderResolver(OrderDataUtil orderDataUtil) {
        this.orderDataUtil = orderDataUtil;

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.inventoryStub = InventoryServiceGrpc.newBlockingStub(channel);
    }

    public List<Order> getOrders(String customerUsername) throws IOException {
        return orderDataUtil.loadOrders()
                .stream()
                .filter(order -> order.getCustomerUsername().equalsIgnoreCase(customerUsername))
                .toList();
    }

    public Order getOrder(String id) throws IOException {
        return orderDataUtil.loadOrders()
                .stream()
                .filter(order -> order.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public Order createOrder(OrderInput input) throws IOException {
    // Validate inventory and update stock using gRPC
    List<OrderProduct> products = input.getProducts().stream()
        .map(productInput -> {
            // Check availability
            ProductResponse response = inventoryStub.checkAvailability(
                    ProductRequest.newBuilder()
                            .setProductId(productInput.getProductId())
                            .setQuantity(productInput.getQuantity())
                            .build()
            );

            if (!response.getAvailable()) {
                throw new RuntimeException("Product " + productInput.getProductId() +
                        " is unavailable. Available quantity: " + response.getAvailableQuantity());
            }

            // Update inventory
            inventoryStub.updateInventory(
                    InventoryServiceOuterClass.UpdateInventoryRequest.newBuilder()
                            .setProductId(productInput.getProductId())
                            .setQuantity(productInput.getQuantity())
                            .build()
            );

            return new OrderProduct(
                    response.getProductId(),
                    productInput.getName(),
                    productInput.getQuantity(),
                    productInput.getUnitPrice()
            );
        })
        .collect(Collectors.toList());

        // Create a new order
        Order newOrder = new Order(
                UUID.randomUUID().toString(),
                input.getCustomerUsername(),
                products,
                calculateTotalAmount(products),
                "Pending",
                LocalDateTime.now().toString()
        );
    
        // Save the new order
        List<Order> orders = orderDataUtil.loadOrders();
        orders.add(newOrder);
        orderDataUtil.saveOrders(orders);
    
        return newOrder;
    }


    private float calculateTotalAmount(List<OrderProduct> products) {
        return (float) products.stream()
                .mapToDouble(product -> product.getQuantity() * (product.getUnitPrice() != null ? product.getUnitPrice() : 0))
                .sum();
    }
}
