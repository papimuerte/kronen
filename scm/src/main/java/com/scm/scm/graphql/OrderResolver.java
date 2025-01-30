
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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class OrderResolver {

    private final OrderDataUtil orderDataUtil;
    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

    public OrderResolver(OrderDataUtil orderDataUtil) {
        this.orderDataUtil = orderDataUtil;

        // Initialize gRPC channel for inventory service
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.inventoryStub = InventoryServiceGrpc.newBlockingStub(channel);
    }

    // Query to retrieve orders by customerUsername
    @QueryMapping
    public List<Order> ordersByCustomer(@Argument String customerUsername) throws IOException {
        List<Order> orders = orderDataUtil.loadOrders();
        return orders.stream()
                .filter(order -> order.getCustomerUsername().equalsIgnoreCase(customerUsername))
                .toList();
    }

    @QueryMapping
    public List<Order> allOrders() throws IOException {
        return orderDataUtil.loadOrders(); // Return all orders
    }

    // Query to fetch a single order by ID
    @QueryMapping
    public Order order(@Argument String id) throws IOException {
        return orderDataUtil.loadOrders()
                .stream()
                .filter(order -> order.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // Mutation to create a new order
    @MutationMapping
    public Order createOrder(@Argument OrderInput input) throws IOException {
        // Validate inventory and update stock using gRPC

        List<OrderProduct> products = input.getProducts().stream()
                .map(productInput -> {
                    // Check product availability via gRPC
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

                    // Update inventory via gRPC
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
                LocalDateTime.now().toString(),
                input.getCompanyName(),
                input.getEmail(),
                input.getAddress(),
                input.getPhoneNumber(),
                input.getNotes()
        );
            

        // Save the new order
        List<Order> orders = orderDataUtil.loadOrders();
        orders.add(newOrder);
        orderDataUtil.saveOrders(orders);

        return newOrder;
    }

    // Helper method to calculate total amount
    private float calculateTotalAmount(List<OrderProduct> products) {
        return (float) products.stream()
                .mapToDouble(product -> product.getQuantity() * (product.getUnitPrice() != null ? product.getUnitPrice() : 0))
                .sum();
    }
}
