package com.scm.scm.controller.authController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scm.scm.model.User;
import com.scm.scm.util.UserDataUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
public class AuthController {

    private final UserDataUtil userDataUtil;

    public AuthController(UserDataUtil userDataUtil) {
        this.userDataUtil = userDataUtil;
    }

    @Operation(summary = "Register a new user", description = "Allows a new user to register with a unique username and email.")
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

    @Operation(summary = "Login a user", description = "Authenticates a user and generates a JWT token upon successful login.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        try {
            List<User> users = userDataUtil.loadUsers();
            User user = users.stream()
                .filter(u -> u.getUsername().equals(loginRequest.getUsername()) && u.getPassword().equals(loginRequest.getPassword()))
                .findFirst()
                .orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ungültige Anmeldedaten.");
            }

            @SuppressWarnings("deprecation")
            String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .claim("phone", user.getPhone())
                .claim("address", user.getAddress())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 Stunde
                .signWith(Keys.hmacShaKeyFor("MeinGeheimerSchlüsselMitMindestens32Zeichen".getBytes()), SignatureAlgorithm.HS256)
                .compact();

            return ResponseEntity.ok(jwt);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Zugriff auf Benutzerdaten.");
        }
    }

    @Operation(summary = "Validate a JWT token", description = "Verifies the validity of a JWT token and returns its claims.")
    @GetMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestParam String token) {
        try {
            final SecretKey key = Keys.hmacShaKeyFor("MeinGeheimerSchlüsselMitMindestens32Zeichen".getBytes());

            Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ungültig.");
        }
    }
}
