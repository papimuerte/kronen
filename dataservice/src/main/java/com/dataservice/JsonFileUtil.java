package com.dataservice;

// Utility class for reading and writing JSON files using Jackson ObjectMapper.

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonFileUtil {
    private static final String BASE_PATH = "data/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Reads a JSON file and converts it into a list of objects of the specified type.
    public <T> List<T> readJsonFile(String fileName, TypeReference<List<T>> typeReference) throws IOException {
        File file = new File(BASE_PATH + fileName);
        if (!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }
        return objectMapper.readValue(file, typeReference);
    }

    // Writes a list of objects to a JSON file.
    public <T> void writeJsonFile(String fileName, List<T> data) throws IOException {
        objectMapper.writeValue(new File(BASE_PATH + fileName), data);
    }
}