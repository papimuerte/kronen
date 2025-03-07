package com.backend.user.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.backend.model.User;
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

    // Update an existing user
    public User updateUser(String username, User updatedUser) throws IOException {
        String url = DATA_SERVICE_URL + "/" + username; // Endpoint to update a user
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<User> request = new HttpEntity<>(updatedUser, headers);
        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.PUT, request, User.class);
        
        return response.getBody(); // Return the updated user
    }

    /**
     * Deletes a user by sending a DELETE request to the external data service.
     * @param username The username of the user to delete.
     * @return True if deletion was successful, false if the user was not found.
     * @throws IOException If an error occurs during the request.
     */
    public boolean deleteUser(String username) throws IOException {
        String url = DATA_SERVICE_URL + "/" + username; // Endpoint to delete a user
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);

        return response.getStatusCode().is2xxSuccessful(); // Return true if deletion was successful
    }

}
