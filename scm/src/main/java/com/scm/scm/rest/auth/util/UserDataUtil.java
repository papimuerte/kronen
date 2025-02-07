package com.scm.scm.rest.auth.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scm.scm.rest.product.model.User;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class UserDataUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File userFile;

    public UserDataUtil() throws IOException {
        URL resource = getClass().getClassLoader().getResource("data/users.json");
        if (resource == null) {
            System.err.println("File not found: users.json");
            throw new IOException("users.json file not found in resources/data/");
        } else {
            System.out.println("File located at: " + resource.getPath());
        }
        this.userFile = new File(resource.getFile());
    }
    

    public List<User> loadUsers() throws IOException {
        return objectMapper.readValue(userFile, new TypeReference<>() {});
    }

    public void saveUsers(List<User> users) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(userFile, users);
    }
}
