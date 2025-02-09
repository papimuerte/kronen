package com.dataservice.controller;


import com.dataservice.model.User;
import com.dataservice.JsonFileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
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

}

