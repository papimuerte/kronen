package com.dataservice.controller;

import com.dataservice.model.User;
import com.dataservice.JsonFileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users-data")
public class UserController {

    private static final String FILE_PATH = "data/users.json";
    private final JsonFileUtil jsonFileUtil;

    public UserController(JsonFileUtil jsonFileUtil) {
        this.jsonFileUtil = jsonFileUtil;
    }

    @GetMapping
    public List<User> getAllUsers() throws IOException {
        return jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<User>>() {});
    }

    @PostMapping
    public User addUser(@RequestBody User newUser) throws IOException {
        List<User> users = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<User>>() {});
        users.add(newUser);
        jsonFileUtil.writeJsonFile(FILE_PATH, users);
        return newUser;
    }

    // Update user data by username
    @PutMapping("/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User updatedUser) throws IOException {
        List<User> users = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<User>>() {});

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (updatedUser.getPassword() != null) user.setPassword(updatedUser.getPassword());
                if (updatedUser.getRole() != null) user.setRole(updatedUser.getRole());
                if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
                if (updatedUser.getPhoneNumber() != null) user.setPhoneNumber(updatedUser.getPhoneNumber());
                if (updatedUser.getAddress() != null) user.setAddress(updatedUser.getAddress());
                if (updatedUser.getcompanyName() != null) user.setcompanyName(updatedUser.getcompanyName());

                jsonFileUtil.writeJsonFile(FILE_PATH, users);
                return user;
            }
        }

        throw new RuntimeException("User with username " + username + " not found.");
    }

    // Delete a user by username
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) throws IOException {
        List<User> users = jsonFileUtil.readJsonFile(FILE_PATH, new TypeReference<List<User>>() {});

        boolean removed = users.removeIf(user -> user.getUsername().equals(username));

        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        jsonFileUtil.writeJsonFile(FILE_PATH, users);
        return ResponseEntity.ok("User successfully deleted.");
    }
}
