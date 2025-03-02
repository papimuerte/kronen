package com.dataservice;

// Utility class for reading and writing JSON files using Jackson ObjectMapper.

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonFileUtil {
    private static final String BASE_PATH = "data/";
    private final ObjectMapper objectMapper = new ObjectMapper();

 public <T> List<T> readJsonFile(String fileName, TypeReference<List<T>> typeReference) throws IOException {
    File file = new File(BASE_PATH + fileName);
    if (!file.exists()) {
        throw new IOException("File not found: " + file.getAbsolutePath());
    }

    // JSON als generelles Map-Objekt lesen
    var rootNode = objectMapper.readTree(file);

    // Falls die Datei eine "menu"-Struktur hat, extrahiere die Produkte
    if (rootNode.has("menu")) {
        var menuNode = rootNode.get("menu");
        List<T> allProducts = new ArrayList<>();

        // Iteriere durch alle Kategorien in "menu"
        menuNode.fields().forEachRemaining(entry -> {
            try {
                List<T> products = objectMapper.readValue(entry.getValue().toString(), typeReference);
                allProducts.addAll(products);
            } catch (IOException e) {
                throw new RuntimeException("Error parsing category: " + entry.getKey(), e);
            }
        });

        return allProducts;
    }

    // Falls das JSON bereits eine flache Liste ist, einfach zurückgeben
    return objectMapper.readValue(file, typeReference);
}



    // Writes a list of objects to a JSON file.
    public <T> void writeJsonFile(String fileName, List<T> data) throws IOException {
        objectMapper.writeValue(new File(BASE_PATH + fileName), data);
    }
}