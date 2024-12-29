package com.scm.scm.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scm.scm.model.Order;

@Component
public class OrderDataUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File orderFile;

    public OrderDataUtil() throws IOException {
        URL resource = getClass().getClassLoader().getResource("data/orders.json");
        if (resource == null) {
            throw new IOException("orders.json not found in resources/data/");
        }
        this.orderFile = new File(resource.getFile());
    }

    public List<Order> loadOrders() throws IOException {
        return objectMapper.readValue(orderFile, new TypeReference<List<Order>>() {});
    }

    public void saveOrders(List<Order> orders) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(orderFile, orders);
    }
}
