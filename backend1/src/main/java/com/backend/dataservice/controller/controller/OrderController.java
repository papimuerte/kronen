package com.backend.dataservice.controller.controller;

// REST controller for managing order data stored in a JSON file.

import com.backend.model.Order;
import com.backend.dataservice.util.JsonFileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController("dataserviceOrderController")  // 💡 Eindeutiger Name
@RequestMapping("/orders-data")
public class OrderController {

    // Logger instance for logging operations related to order management.
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // File path where order data is stored in JSON format.
    private static final String FILE_PATH = "backend1/src/main/resources/data/orders.json";
    private final JsonFileUtil jsonFileUtil;

    // Constructor for initializing JsonFileUtil dependency.
    public OrderController(JsonFileUtil jsonFileUtil) {
        this.jsonFileUtil = jsonFileUtil;
    }

    // Retrieves all orders from the JSON file.
    @GetMapping
    public List<Order> getAllOrders() throws IOException {
        logger.info("Fetching all orders from file: {}", FILE_PATH);
        List<Order> orders = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Order>>() {});
        
        if (orders.isEmpty()) {
            logger.warn("No orders found in {}", FILE_PATH);
        } else {
            logger.info("Successfully retrieved {} orders", orders.size());
        }
        
        return orders;
    }

    // Adds a new order to the JSON file and returns the updated order list.
    @PostMapping
    public List<Order> addOrder(@RequestBody Order newOrder) throws IOException {
        logger.info("Received request to add new order: {}", newOrder);

        List<Order> orders = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Order>>() {});
        orders.add(newOrder);

        logger.info("Saving updated order list with {} total orders", orders.size());
        jsonFileUtil.writeJsonFile(FILE_PATH, orders);

        logger.info("New order successfully added: {}", newOrder.getId());
        return orders;
    }

    // New Function: Update Order
    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable String orderId, @RequestBody Order updatedOrder) throws IOException {
        logger.info("Received request to update order with ID: {}", orderId);

        List<Order> orders = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<Order>>() {});

        Optional<Order> existingOrderOpt = orders.stream()
                .filter(order -> order.getId().equalsIgnoreCase(orderId))
                .findFirst();

        if (existingOrderOpt.isPresent()) {
            Order existingOrder = existingOrderOpt.get();

            // Update the order details
            existingOrder.setCustomerUsername(updatedOrder.getCustomerUsername());
            existingOrder.setProducts(updatedOrder.getProducts());
            existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setCompanyName(updatedOrder.getCompanyName());
            existingOrder.setEmail(updatedOrder.getEmail());
            existingOrder.setAddress(updatedOrder.getAddress());
            existingOrder.setPhoneNumber(updatedOrder.getPhoneNumber());
            existingOrder.setNotes(updatedOrder.getNotes());

            logger.info("Order updated successfully: {}", existingOrder.getId());

            // Save updated orders back to file
            jsonFileUtil.writeJsonFile(FILE_PATH, orders);
            return existingOrder;
        } else {
            logger.warn("Order with ID {} not found", orderId);
            throw new RuntimeException("Order not found: " + orderId);
        }
    }
}


