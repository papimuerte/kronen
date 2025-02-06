package com.scm.scm.graphql.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scm.scm.rest.model.Product;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class ProductDataUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File productFile;

    public ProductDataUtil() throws IOException {
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
}
