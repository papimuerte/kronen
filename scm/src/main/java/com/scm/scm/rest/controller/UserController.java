package com.scm.scm.rest.controller;

import com.scm.scm.rest.model.User;
import com.scm.scm.rest.util.UserDataUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users.")
    @GetMapping
    public Mono<ResponseEntity<List<User>>> getAllUsers() {
        return Mono.fromCallable(userDataUtil::loadUsers)
                   .map(users -> ResponseEntity.ok(users))
                   .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }

    @Operation(summary = "Get user by ID", description = "Fetches details of a specific user using their username.")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable String id) {
        return Mono.fromCallable(userDataUtil::loadUsers)
                .map(users -> users.stream()
                        .filter(user -> user.getUsername().equals(id))
                        .findFirst()
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().build()));
    }
    

    @Operation(summary = "Update user by ID", description = "Updates the details of a specific user using their username. Only non-null fields will be updated.")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<? extends Object>> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return Mono.fromCallable(userDataUtil::loadUsers)
                   .flatMap(users -> {
                       for (User user : users) {
                           if (user.getUsername().equals(id)) {
                               updateNonNullFields(updatedUser, user);
                               return saveUsersMono(users)
                                   .then(Mono.just(ResponseEntity.ok("Registrierung erfolgreich aktualisiert.")));
                           }
                       }
                       return Mono.just(ResponseEntity.notFound().build());
                   })
                   .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Fehler beim Aktualisieren des Benutzers.")));
    }
    
    @Operation(summary = "Delete user by ID", description = "Deletes a specific user using their username.")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<? extends Object>> deleteUser(@PathVariable String id) {
        return Mono.fromCallable(userDataUtil::loadUsers)
                   .flatMap(users -> {
                       boolean removed = users.removeIf(user -> user.getUsername().equals(id));
                       if (removed) {
                           return saveUsersMono(users).map(saved -> ResponseEntity.ok("Benutzer erfolgreich gelöscht."));
                       }
                       return Mono.just(ResponseEntity.notFound().build());
                   })
                   .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body("Fehler beim Löschen des Benutzers.")));
    }

    private Mono<Void> saveUsersMono(List<User> users) {
        return Mono.fromRunnable(() -> {
            try {
                userDataUtil.saveUsers(users);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void updateNonNullFields(User source, User target) {
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getRole() != null) target.setRole(source.getRole());
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getPhoneNumber() != null) target.setPhoneNumber(source.getPhoneNumber());
        if (source.getAddress() != null) target.setAddress(source.getAddress());
    }
}