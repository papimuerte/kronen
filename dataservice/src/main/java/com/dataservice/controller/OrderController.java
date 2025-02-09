package com.dataservice.controller;

import com.dataservice.model.Order;
import com.dataservice.JsonFileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders-data")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private static final String FILE_PATH = "data/orders.json";
    private final JsonFileUtil jsonFileUtil;

    public OrderController(JsonFileUtil jsonFileUtil) {
        this.jsonFileUtil = jsonFileUtil;
    }

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
}
