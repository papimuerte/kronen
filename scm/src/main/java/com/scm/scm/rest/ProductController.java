package com.scm.scm.rest;

import com.scm.scm.model.Product;
import com.scm.scm.util.ProductDataUtil;
import java.io.IOException;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/products")
public class ProductController {

    private final ProductDataUtil productDataUtil;

    public ProductController(ProductDataUtil productDataUtil) {
        this.productDataUtil = productDataUtil;
    }

    // Alle Produkte abrufen (READ)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productDataUtil.loadProducts();
            return ResponseEntity.ok(products);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Ein Produkt nach ID abrufen (READ)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        try {
            List<Product> products = productDataUtil.loadProducts();
            return products.stream()
                    .filter(product -> product.getProductId().equals(id))
                    .findFirst()
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Neues Produkt hinzufügen (CREATE)
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product newProduct) {
        try {
            List<Product> products = productDataUtil.loadProducts();
            products.add(newProduct);
            productDataUtil.saveProducts(products);
            return ResponseEntity.ok("Produkt hinzugefügt.");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Fehler beim Speichern des Produkts.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        try {
            List<Product> products = productDataUtil.loadProducts();
            for (Product product : products) {
                if (product.getProductId().equals(id)) {
                    boolean isUpdated = updateNonNullFields(updatedProduct, product);
                    if (isUpdated) {
                        productDataUtil.saveProducts(products);
                        return ResponseEntity.ok("Produkt aktualisiert.");
                    } else {
                        return ResponseEntity.ok("Keine Änderungen vorgenommen.");
                    }
                }
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Fehler beim Aktualisieren des Produkts.");
        }
    }

    private boolean updateNonNullFields(Object source, Object target) {
        boolean isUpdated = false;

        for (var field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true); // Allow access to private fields
            try {
                Object sourceValue = field.get(source);
                if (sourceValue != null) { // Update only non-null fields
                    Object targetValue = field.get(target);
                    if (!sourceValue.equals(targetValue)) { // Update if value has changed
                        field.set(target, sourceValue);
                        isUpdated = true;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Fehler beim Aktualisieren der Felder.", e);
            }
        }

        return isUpdated;
    }

    // Produkt löschen (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        try {
            List<Product> products = productDataUtil.loadProducts();
            boolean removed = products.removeIf(product -> product.getProductId().equals(id));
            if (removed) {
                productDataUtil.saveProducts(products);
                return ResponseEntity.ok("Produkt gelöscht.");
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Fehler beim Löschen des Produkts.");
        }
    }
}