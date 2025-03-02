package com.restusers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restusers.model.User;
import com.restusers.util.UserDataUtil;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    private final UserDataUtil userDataUtil;

    // Constructor injection for UserDataUtil dependency
    public UserController(UserDataUtil userDataUtil) {
        this.userDataUtil = userDataUtil;
    }

    // Endpoint to retrieve all registered users
    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users.")
    @GetMapping
    public Mono<ResponseEntity<List<User>>> getAllUsers() {
        return Mono.fromCallable(userDataUtil::loadUsers) // Load users asynchronously
                   .map(users -> ResponseEntity.ok(users)) // Return a 200 OK response with users list
                   .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build())); // Handle errors
    }

    // Endpoint to retrieve a user by their username
    @Operation(summary = "Get user by ID", description = "Fetches details of a specific user using their username.")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable String id) {
        return Mono.fromCallable(userDataUtil::loadUsers)
                .map(users -> users.stream()
                        .filter(user -> user.getUsername().equals(id)) // Find user by username
                        .findFirst()
                        .map(ResponseEntity::ok) // Return user if found
                        .orElse(ResponseEntity.notFound().build())) // Return 404 if user not found
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build())); // Handle errors
    }
    
    // Endpoint to update a user's information
    @Operation(summary = "Update user by ID", description = "Updates the details of a specific user using their username. Only non-null fields will be updated.")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return Mono.fromCallable(() -> userDataUtil.loadUser(id)) // Load user by ID
                   .flatMap(userOpt -> userOpt.map(user -> {
                       updateNonNullFields(updatedUser, user); // Update only non-null fields
    
                       return Mono.fromCallable(() -> { 
                           userDataUtil.updateUser(id, user); // ✅ Now properly handles IOException
                           return ResponseEntity.ok("User successfully updated.");
                       });
    
                   }).orElseGet(() -> {
                       return Mono.just(ResponseEntity.notFound().build()); // Return 404 if user not found
                   }))
                   .onErrorResume(e -> {
                       return Mono.just(ResponseEntity.internalServerError().body("Error updating user."));
                   });
    }


    // Endpoint to delete a user by ID
    @Operation(summary = "Delete user by ID", description = "Deletes a specific user using their username.")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<? extends Object>> deleteUser(@PathVariable String id) {
        return Mono.fromCallable(() -> userDataUtil.deleteUser(id)) // Call delete function directly
                   .flatMap(deleted -> deleted ? Mono.just(ResponseEntity.ok("User successfully deleted.")) 
                                               : Mono.just(ResponseEntity.notFound().build()))
                   .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Error deleting user.")));
    }
    

    // Helper method to save all users asynchronously
    private Mono<Void> saveUsersMono(List<User> users) {
        return Mono.fromRunnable(() -> {
            try {
                userDataUtil.saveUsers(users);
            } catch (IOException e) {
                throw new RuntimeException(e); // Convert checked exception to runtime exception
            }
        });
    }

    // Helper method to save a single user asynchronously
    private Mono<Void> saveUserMono(User user) {
        return Mono.fromRunnable(() -> {
            try {
                userDataUtil.saveUser(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Updates only non-null fields of the target user with values from the source user
    private void updateNonNullFields(User source, User target) {
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getRole() != null) target.setRole(source.getRole());
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getPhoneNumber() != null) target.setPhoneNumber(source.getPhoneNumber());
        if (source.getAddress() != null) target.setAddress(source.getAddress());
    }
}
