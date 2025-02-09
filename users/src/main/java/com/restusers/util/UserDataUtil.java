package com.restusers.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restusers.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserDataUtil {
    private final RestTemplate restTemplate = new RestTemplate(); // REST client for making HTTP requests
    private static final String DATA_SERVICE_URL = "http://localhost:8090/users-data"; // URL of the data service

    /**
     * Loads all users from the external data service.
     * @return A list of User objects retrieved from the service.
     * @throws IOException If an error occurs during the request.
     */
    public List<User> loadUsers() throws IOException {
        ResponseEntity<User[]> response = restTemplate.getForEntity(DATA_SERVICE_URL, User[].class);
        return Arrays.asList(response.getBody());
    }

    /**
     * Loads a specific user by their username from the external data service.
     * @param id The username of the user to retrieve.
     * @return An Optional containing the user if found, otherwise empty.
     * @throws IOException If an error occurs during the request.
     */
    public Optional<User> loadUser(String id) throws IOException {
        ResponseEntity<User[]> response = restTemplate.getForEntity(DATA_SERVICE_URL, User[].class);
        return Arrays.stream(response.getBody())
                     .filter(user -> user.getUsername().equals(id))
                     .findFirst();
    }

    /**
     * Saves a single user by sending a POST request to the external data service.
     * @param user The user object to be saved.
     * @throws IOException If an error occurs during the request.
     */
    public void saveUser(User user) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // Sets the content type to JSON
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        restTemplate.exchange(DATA_SERVICE_URL, HttpMethod.POST, request, Void.class);
    }

    /**
     * Saves multiple users by sending a POST request to the external data service.
     * @param users A list of user objects to be saved.
     * @throws IOException If an error occurs during the request.
     */
    public void saveUsers(List<User> users) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // Sets the content type to JSON
        HttpEntity<List<User>> request = new HttpEntity<>(users, headers);
        restTemplate.exchange(DATA_SERVICE_URL, HttpMethod.POST, request, Void.class);
    }
}
