package org.example.backend.service;

import org.example.backend.model.User;
import org.example.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User createUser(String username, String password) {
        String hash = encoder.encode(password);
        User u = new User(null, username, hash);
        return repo.save(u);
    }

    public boolean validateLogin(String username, String password) {
        return repo.findByUsername(username)
                .map(u -> encoder.matches(password, u.passwordHash()))
                .orElse(false);
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
