
package com.dataservice.controller;

// REST controller for managing user data stored in a JSON file.

import com.dataservice.model.User;
import com.dataservice.JsonFileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users-data")
public class UserController {

    // File path where user data is stored in JSON format.
    private static final String FILE_PATH = "data/users.json";
    private final JsonFileUtil jsonFileUtil;

    // Constructor to initialize JsonFileUtil dependency.
    public UserController(JsonFileUtil jsonFileUtil) {
        this.jsonFileUtil = jsonFileUtil;
    }

    // Retrieves all users from the JSON file.
    @GetMapping
    public List<User> getAllUsers() throws IOException {
        return jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<User>>() {});
    }

    // Adds a new user to the JSON file and returns the added user.
    @PostMapping
    public User addUser(@RequestBody User newUser) throws IOException {
        List<User> users = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<User>>() {});
        users.add(newUser);
        jsonFileUtil.writeJsonFile(FILE_PATH, users);
        return newUser;
    }
}
