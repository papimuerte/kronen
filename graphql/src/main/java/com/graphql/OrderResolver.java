package com.graphql;

import com.graphql.model.Order;
import com.graphql.model.OrderInput;
import com.graphql.model.OrderProduct;
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
    private final ProductServiceUtil productServiceUtil; // Util to interact with product service

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

    // Mutation to create a new order
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

        // Create a new order after stock is confirmed
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
        orderDataUtil.saveOrder(newOrder);

        return newOrder;
    }

    // Helper method to calculate total amount
    private float calculateTotalAmount(List<OrderProduct> products) {
        return (float) products.stream()
                .mapToDouble(product -> product.getQuantity() * (product.getUnitPrice() != null ? product.getUnitPrice() : 0))
                .sum();
    }
}

