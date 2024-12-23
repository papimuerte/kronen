package com.scm.scm.controller;

import com.scm.scm.model.User;
import com.scm.scm.util.UserDataUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok(users);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
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
                    .orElse(ResponseEntity.notFound().build());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
