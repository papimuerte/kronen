package com.scm.scm.controller;

import com.scm.scm.model.User;
import com.scm.scm.util.UserDataUtil;
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

    // GET /users: Abrufen aller Benutzer
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

    // GET /users/{email}: Anzeigen eines spezifischen Benutzers anhand der E-Mail
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

    // PUT /users/{email}: Aktualisieren von Benutzerdaten mit Validierung
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

    // POST /users: Neuer Benutzer mit Validierung
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

    // DELETE /users/{email}: Löschen eines Benutzers
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

    // Hilfsmethode zum Aktualisieren von Feldern
    private void updateNonNullFields(User source, User target) {
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getRole() != null) target.setRole(source.getRole());
        if (source.getPhone() != null) target.setPhone(source.getPhone());
        if (source.getAddress() != null) target.setAddress(source.getAddress());
    }
}
