package com.dataservice;

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

public <T> List<T> readJsonFile(String fileName, TypeReference<List<T>> typeReference) throws IOException {
    File file = new File(BASE_PATH + fileName);
    if (!file.exists()) {
        throw new IOException("File not found: " + file.getAbsolutePath());
    }
    return objectMapper.readValue(file, typeReference);
}


public <T> void writeJsonFile(String fileName, List<T> data) throws IOException {
    objectMapper.writeValue(new File(BASE_PATH + fileName), data);
}

}
