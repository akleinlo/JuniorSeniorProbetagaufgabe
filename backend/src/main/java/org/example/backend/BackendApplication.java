package org.example.backend;

import org.example.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserService userService) {
        return args -> {
            if (userService.findByUsername("admin").isEmpty()) {
                userService.createUser("admin", "admin123");
                System.out.println("Default user admin/admin123 created");
            }
        };
    }

}
