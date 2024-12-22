package com.scm.scm.controller.authController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.scm.scm.model.User;
import com.scm.scm.util.UserDataUtil;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDataUtil userDataUtil;

    public AuthController(UserDataUtil userDataUtil) {
        this.userDataUtil = userDataUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            List<User> users = userDataUtil.loadUsers();
            if (users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Benutzername existiert bereits.");
            }
            users.add(user);
            userDataUtil.saveUsers(users);
            return ResponseEntity.ok("Benutzer erfolgreich registriert.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Zugriff auf Benutzerdaten.");
        }
    }
}
