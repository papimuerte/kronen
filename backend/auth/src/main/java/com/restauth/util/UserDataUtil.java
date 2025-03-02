package com.restauth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restauth.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for handling user data operations such as loading and saving users.
 */
@Component
public class UserDataUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    
    // URL of the external data service providing user data
    private static final String DATA_SERVICE_URL = "http://localhost:8090/users-data";

    /**
     * Loads the list of users from the external data service.
     *
     * @return List of users retrieved from the service.
     * @throws IOException if there is an error accessing user data.
     */
    public List<User> loadUsers() throws IOException {
        try {
            ResponseEntity<User[]> response = restTemplate.getForEntity(DATA_SERVICE_URL, User[].class);
    
            // Debug logging to track API response
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + Arrays.toString(response.getBody()));
    
            if (response.getBody() == null) {
                throw new IOException("Received null response from data service");
            }
    
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            e.printStackTrace(); // Log stack trace for debugging
            throw new IOException("Error accessing user data: " + e.getMessage());
        }
    }

    /**
     * Saves a new user by sending a POST request to the external data service.
     *
     * @param user The user to be saved.
     * @throws IOException if there is an error while sending the request.
     */
    public void saveUser(User user) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // Set JSON content type
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        // Send the HTTP POST request to save the user
        restTemplate.exchange(DATA_SERVICE_URL, HttpMethod.POST, request, Void.class);
    }
}
