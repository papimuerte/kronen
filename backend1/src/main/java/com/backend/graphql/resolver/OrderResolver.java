package com.backend.graphql.resolver;

import com.backend.model.Order;
import com.backend.model.OrderInput;
import com.backend.model.OrderProduct;
import com.backend.model.Product;
import com.backend.graphql.util.OrderDataUtil;
import com.backend.graphql.util.ProductServiceUtil;
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
    private final ProductServiceUtil productServiceUtil;

    public OrderResolver(OrderDataUtil orderDataUtil, ProductServiceUtil productServiceUtil) {
        this.orderDataUtil = orderDataUtil;
        this.productServiceUtil = productServiceUtil;
    }

    // Query to retrieve orders by customerUsername
    @QueryMapping
    public List<Order> ordersByCustomer(@Argument String customerUsername) throws IOException {
        return orderDataUtil.loadOrders().stream()
                .filter(order -> order.getCustomerUsername().equalsIgnoreCase(customerUsername))
                .toList();
    }

    // Query to retrieve all orders
    @QueryMapping
    public List<Order> allOrders() throws IOException {
        return orderDataUtil.loadOrders();
    }

    // Query to fetch a single order by ID
    @QueryMapping
    public Order order(@Argument String id) throws IOException {
        return orderDataUtil.loadOrders().stream()
                .filter(order -> order.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    // Existing Mutation: Create a new order
    @MutationMapping
    public Order createOrder(@Argument OrderInput input) throws IOException {
        List<OrderProduct> products = input.getProducts().stream()
                .map(productInput -> new OrderProduct(
                        productInput.getProductId(),
                        productInput.getName(),
                        productInput.getQuantity(),
                        productInput.getUnitPrice()
                ))
                .collect(Collectors.toList());
    
        // Create the order
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
    
        orderDataUtil.saveOrder(newOrder);
        return newOrder;
    }

    // Helper: Calculate total amount
    private float calculateTotalAmount(List<OrderProduct> products) {
        return (float) products.stream()
                .mapToDouble(product -> product.getQuantity() * (product.getUnitPrice() != null ? product.getUnitPrice() : 0))
                .sum();
    }
    
    @MutationMapping
    public Order updateOrderStatus(@Argument String orderId, @Argument String newStatus) throws IOException {
        List<Order> orders = orderDataUtil.loadOrders();
    
        // Find the order by ID
        Order orderToUpdate = orders.stream()
                .filter(order -> order.getId().equalsIgnoreCase(orderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    
        // Validate that the new status is allowed
        List<String> validStatuses = List.of("Pending", "Shipped", "Done");
        if (!validStatuses.contains(newStatus)) {
            throw new IllegalArgumentException("Invalid status: " + newStatus);
        }
    
        // Update the order status
        orderToUpdate.setStatus(newStatus);
    
        // Update the order instead of saving a new one
        orderDataUtil.updateOrder(orderId, orderToUpdate);
    
        return orderToUpdate;
    }
    
}

