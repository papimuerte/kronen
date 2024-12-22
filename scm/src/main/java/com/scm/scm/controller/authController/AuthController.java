package com.scm.scm.controller.authController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.scm.scm.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDataUtil userDataUtil;

    public AuthController(UserDataUtil userDataUtil) {
        this.userDataUtil = userDataUtil;
    }

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
}
