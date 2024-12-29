package com.scm.scm.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scm.scm.model.Product;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Component
public class InventoryDataUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File productFile;

    public InventoryDataUtil() throws IOException {
        URL resource = getClass().getClassLoader().getResource("data/products.json");
        if (resource == null) {
            System.err.println("File not found: products.json");
            throw new IOException("products.json file not found in resources/data/");
        }
        this.productFile = new File(resource.getFile());
    }

    public List<Product> loadProducts() throws IOException {
        return objectMapper.readValue(productFile, new TypeReference<>() {});
    }

    public void saveProducts(List<Product> products) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(productFile, products);
    }

    /**
     * Retrieves inventory information for a specific productId.
     * @param productId The ID of the product to look for.
     * @return An optional containing the product if found, otherwise empty.
     * @throws IOException If an error occurs while accessing the file.
     */
    public Optional<Product> getProductById(String productId) throws IOException {
        return loadProducts().stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
    }

    /**
     * Retrieves the available quantity for a specific productId.
     * @param productId The ID of the product to look for.
     * @return The available quantity, or -1 if the product is not found.
     * @throws IOException If an error occurs while accessing the file.
     */
    public int getAvailableQuantity(String productId) throws IOException {
        return getProductById(productId)
                .map(Product::getAvailableQuantity)
                .orElse(-1); // Return -1 if the product is not found
    }
}
