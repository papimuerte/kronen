package com.scm.scm.graphql;

import com.scm.scm.model.Order;
import com.scm.scm.model.OrderProduct;
import com.scm.scm.model.Product;
import com.scm.scm.util.InventoryDataUtil;
import com.scm.scm.util.OrderDataUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderResolver {

    private final OrderDataUtil orderDataUtil;

    @Autowired
    private InventoryDataUtil inventoryDataUtil;

    @Autowired
    public OrderResolver(OrderDataUtil orderDataUtil, InventoryDataUtil inventoryDataUtil) {
        this.orderDataUtil = orderDataUtil;
        this.inventoryDataUtil = inventoryDataUtil;
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
        List<OrderProduct> products = input.getProducts().stream()
                .map(productInput -> {
                    try {
                        Product inventoryProduct = inventoryDataUtil.getProductById(productInput.getProductId())
                                .orElseThrow(() -> new RuntimeException("Product not found: " + productInput.getProductId()));

                        if (inventoryProduct.getAvailableQuantity() < productInput.getQuantity()) {
                            throw new RuntimeException("Insufficient inventory for product: " + productInput.getProductId());
                        }

                        return new OrderProduct(
                                inventoryProduct.getProductId(),
                                inventoryProduct.getName(),
                                productInput.getQuantity(),
                                inventoryProduct.getUnitPrice()
                        );
                    } catch (IOException e) {
                        throw new RuntimeException("Error loading product inventory: " + e.getMessage(), e);
                    }
                })
                .collect(Collectors.toList());

        Order newOrder = new Order(
                UUID.randomUUID().toString(),
                input.getCustomerUsername(),
                products,
                calculateTotalAmount(products),
                "Pending",
                LocalDateTime.now().toString()
        );

        List<Order> orders = orderDataUtil.loadOrders();
        orders.add(newOrder);
        orderDataUtil.saveOrders(orders);

        // Update inventory quantities
        for (OrderProduct orderProduct : products) {
            Product inventoryProduct = inventoryDataUtil.getProductById(orderProduct.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found during inventory update: " + orderProduct.getProductId()));
            inventoryProduct.setAvailableQuantity(inventoryProduct.getAvailableQuantity() - orderProduct.getQuantity());
        }
        inventoryDataUtil.saveProducts(inventoryDataUtil.loadProducts());

        return newOrder;
    }

    private float calculateTotalAmount(List<OrderProduct> products) {
        return (float) products.stream()
                .mapToDouble(product -> product.getQuantity() * (product.getUnitPrice() != null ? product.getUnitPrice() : 0))
                .sum();
    }
}
