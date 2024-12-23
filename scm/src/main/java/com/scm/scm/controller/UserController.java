package com.scm.scm.controller;

import com.scm.scm.model.User;
import com.scm.scm.util.UserDataUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDataUtil userDataUtil;

    public UserController(UserDataUtil userDataUtil) {
        this.userDataUtil = userDataUtil;
    }

    @Operation(summary = "Gibt alle Benutzer zurück", description = "Abrufen aller Benutzer aus dem System")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich abgerufen"),
        @ApiResponse(responseCode = "204", description = "Keine Benutzer gefunden"),
        @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userDataUtil.loadUsers();
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(users);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Gibt einen Benutzer anhand der E-Mail zurück", description = "Abrufen eines spezifischen Benutzers mit seiner E-Mail-Adresse")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Benutzer gefunden"),
        @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden"),
        @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    })
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            List<User> users = userDataUtil.loadUsers();
            return users.stream()
                    .filter(user -> user.getEmail().equalsIgnoreCase(email))
                    .findFirst()
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Aktualisiert Benutzerdaten", description = "Aktualisieren der Daten eines Benutzers anhand seiner E-Mail")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich aktualisiert"),
        @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden"),
        @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    })
    @PutMapping("/{email}")
    public ResponseEntity<String> updateUserByEmail(@PathVariable String email, @Valid @RequestBody User updatedUser) {
        try {
            List<User> users = userDataUtil.loadUsers();
            boolean userUpdated = false;

            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    updateNonNullFields(updatedUser, user);
                    userUpdated = true;
                    break;
                }
            }

            if (userUpdated) {
                userDataUtil.saveUsers(users);
                return ResponseEntity.ok("Benutzer erfolgreich aktualisiert.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Benutzer nicht gefunden.");
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Aktualisieren des Benutzers.");
        }
    }

    @Operation(summary = "Erstellt einen neuen Benutzer", description = "Hinzufügen eines neuen Benutzers in das System")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Benutzer erfolgreich erstellt"),
        @ApiResponse(responseCode = "409", description = "Benutzer mit dieser E-Mail existiert bereits"),
        @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    })
    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User newUser) {
        try {
            List<User> users = userDataUtil.loadUsers();

            if (users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(newUser.getEmail()))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Benutzer mit dieser E-Mail existiert bereits.");
            }

            users.add(newUser);
            userDataUtil.saveUsers(users);
            return ResponseEntity.status(HttpStatus.CREATED).body("Benutzer erfolgreich erstellt.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Erstellen des Benutzers.");
        }
    }

    @Operation(summary = "Löscht einen Benutzer", description = "Entfernt einen Benutzer aus dem System anhand seiner E-Mail")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Benutzer erfolgreich gelöscht"),
        @ApiResponse(responseCode = "404", description = "Benutzer nicht gefunden"),
        @ApiResponse(responseCode = "500", description = "Interner Serverfehler")
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        try {
            List<User> users = userDataUtil.loadUsers();
            boolean removed = users.removeIf(user -> user.getEmail().equalsIgnoreCase(email));

            if (removed) {
                userDataUtil.saveUsers(users);
                return ResponseEntity.ok("Benutzer erfolgreich gelöscht.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Benutzer nicht gefunden.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Löschen des Benutzers.");
        }
    }

    private void updateNonNullFields(User source, User target) {
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getRole() != null) target.setRole(source.getRole());
        if (source.getPhone() != null) target.setPhone(source.getPhone());
        if (source.getAddress() != null) target.setAddress(source.getAddress());
    }
}
