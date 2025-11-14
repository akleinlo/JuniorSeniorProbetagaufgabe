package org.example.backend.controller;

import org.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    // DTO for user request
    public record LoginRequest(String username, String password) {}

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        boolean valid = service.validateLogin(req.username(), req.password());
        if (!valid)
            return ResponseEntity.status(401).body("Ungültige Zugangsdaten");
        return ResponseEntity.ok("Login erfolgreich");
    }

    // Registrierung (Erweiterung)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest req) {
        if (service.findByUsername(req.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Benutzername bereits vergeben");
        }
        service.createUser(req.username(), req.password());
        return ResponseEntity.ok("Registrierung erfolgreich");
    }
}
