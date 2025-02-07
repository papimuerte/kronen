package com.scm.scm.rest.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileStorage<T> {

    private final File file;
    private final ObjectMapper objectMapper;

    public FileStorage(String filePath) {
        this.file = new File(filePath);
        this.objectMapper = new ObjectMapper();
    }

    // Read data from the file
    public List<T> readData(Class<T> clazz) throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    // Write data to the file
    public void writeData(List<T> data) throws IOException {
        objectMapper.writeValue(file, data);
    }
}