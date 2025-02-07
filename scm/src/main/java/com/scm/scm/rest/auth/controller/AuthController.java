package com.scm.scm.rest.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scm.scm.rest.product.model.User;
import com.scm.scm.rest.auth.util.UserDataUtil;

import reactor.core.publisher.Mono;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
public class AuthController {

    private final UserDataUtil userDataUtil;

    public AuthController(UserDataUtil userDataUtil) {
        this.userDataUtil = userDataUtil;
    }

    @Operation(summary = "Register a new user", description = "Allows a new user to register with a unique username and email.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User successfully registered"),
        @ApiResponse(responseCode = "400", description = "User already exists"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody User user) {
        return Mono.fromCallable(() -> {
            List<User> users = userDataUtil.loadUsers();
            if (users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Benutzername existiert bereits.");
            }
            users.add(user);
            userDataUtil.saveUsers(users);
            return ResponseEntity.ok("Benutzer erfolgreich registriert. Jetzt Einloggen");
        }).onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim Registrieren des Benutzers.")));
    }

    @Operation(summary = "Login a user", description = "Authenticates a user and generates a JWT token upon successful login.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successful login with JWT token"),
        @ApiResponse(responseCode = "401", description = "Invalid login credentials"),
        @ApiResponse(responseCode = "500", description = "Error accessing user data")
    })
    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, Object>>> login(@RequestBody User loginRequest) {
        return Mono.fromCallable(() -> {
            List<User> users = userDataUtil.loadUsers();
            User user = users.stream()
                    .filter(u -> u.getUsername().equals(loginRequest.getUsername()) && u.getPassword().equals(loginRequest.getPassword()))
                    .findFirst()
                    .orElse(null);
    
            if (user == null) {
                // Explicitly define the map type
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid login credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
    
            // Generate JWT token
            SecretKey secretKey = Keys.hmacShaKeyFor("MeinGeheimerSchlüsselMitMindestens32Zeichen".getBytes());
            @SuppressWarnings("deprecation")
            String jwt = Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("role", user.getRole())
                    .claim("email", user.getEmail())
                    .claim("phoneNumber", user.getPhoneNumber())
                    .claim("address", user.getAddress())
                    .claim("companyName", user.getcompanyName())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();
    
            // Set redirect link based on the user's role
            Map<String, String> links = new HashMap<>();
            links.put("self", "/auth/login");
            links.put("redirect", "admin".equalsIgnoreCase(user.getRole()) ? "/admin" : "/shop");
    
            // Define the response explicitly
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("links", links);
    
            return ResponseEntity.ok(response);
        }).onErrorResume(e -> {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error accessing user data");
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
        });
    }
    

    @Operation(summary = "Validate a JWT token", description = "Verifies the validity of a JWT token and returns its claims.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Valid token with claims"),
        @ApiResponse(responseCode = "401", description = "Invalid or expired token")
    })
    @GetMapping("/validate")
    public Mono<ResponseEntity<Object>> validateToken(
            @RequestParam @Schema(description = "JWT token to be validated") String token) {
        return Mono.fromCallable(() -> {
            SecretKey key = Keys.hmacShaKeyFor("MeinGeheimerSchlüsselMitMindestens32Zeichen".getBytes());
    
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    
            // Explicitly cast claims to Object to fix the type mismatch
            return ResponseEntity.ok((Object) claims);
        }).onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ungültig.")));
    }
}
