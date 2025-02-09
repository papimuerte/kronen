package com.graphql;

import com.graphql.model.Order;
import com.graphql.model.OrderInput;
import com.graphql.model.OrderProduct;
import com.graphql.model.Product;
import com.graphql.util.OrderDataUtil;
import com.graphql.util.ProductServiceUtil;
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

    // ✅ Existing Mutation: Create a new order
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
    
        // Check stock availability
        for (OrderProduct product : products) {
            Product existingProduct = productServiceUtil.getProductById(product.getProductId());
    
            if (existingProduct == null) {
                throw new RuntimeException("Product not found: " + product.getProductId());
            }
    
            if (existingProduct.getAvailableQuantity() < product.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getProductId());
            }
        }
    
        // Deduct stock
        for (OrderProduct product : products) {
            productServiceUtil.updateProductQuantity(
                    product.getProductId(),
                    productServiceUtil.getProductById(product.getProductId()).getAvailableQuantity() - product.getQuantity()
            );
        }
    
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
}
