package com.scm.scm.controller;

import com.scm.scm.model.User;
import com.scm.scm.util.UserDataUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    private final UserDataUtil userDataUtil;

    public UserController(UserDataUtil userDataUtil) {
        this.userDataUtil = userDataUtil;
    }

    @Operation(
        summary = "Get all users",
        description = "Retrieves a list of all registered users."
    )
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userDataUtil.loadUsers();
            return ResponseEntity.ok(users);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
        summary = "Get user by ID",
        description = "Fetches details of a specific user using their username."
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        try {
            List<User> users = userDataUtil.loadUsers();
            return users.stream()
                    .filter(user -> user.getUsername().equals(id))
                    .findFirst()
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
        summary = "Update user by ID",
        description = "Updates the details of a specific user using their username. Only non-null fields will be updated."
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        try {
            List<User> users = userDataUtil.loadUsers();
            for (User user : users) {
                if (user.getUsername().equals(id)) {
                    updateNonNullFields(updatedUser, user);
                    userDataUtil.saveUsers(users);
                    return ResponseEntity.ok("Benutzer erfolgreich aktualisiert.");
                }
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Fehler beim Aktualisieren des Benutzers.");
        }
    }

    @Operation(
        summary = "Delete user by ID",
        description = "Deletes a specific user using their username."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            List<User> users = userDataUtil.loadUsers();
            boolean removed = users.removeIf(user -> user.getUsername().equals(id));
            if (removed) {
                userDataUtil.saveUsers(users);
                return ResponseEntity.ok("Benutzer erfolgreich gelöscht.");
            }
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Fehler beim Löschen des Benutzers.");
        }
    }

    private void updateNonNullFields(User source, User target) {
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getRole() != null) target.setRole(source.getRole());
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getPhone() != null) target.setPhone(source.getPhone());
        if (source.getAddress() != null) target.setAddress(source.getAddress());
    }
}
