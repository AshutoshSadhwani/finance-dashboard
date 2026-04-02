package com.finance.finance_dashboard.controller;

import com.finance.finance_dashboard.Repository.UserRepository;
import com.finance.finance_dashboard.entity.Role;
import com.finance.finance_dashboard.entity.Status;
import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user,
                                             Authentication authentication) {

        String loggedInEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (currentUser.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).body("Only ADMIN can create users");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists with this mail");
        }

        userService.createUser(user);
        return ResponseEntity.ok("User Created Successfully");
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<User> updateRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        return ResponseEntity.ok(userService.updateRole(id, role));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<User> updateStatus(
            @PathVariable Long id,
            @RequestParam Status status) {
        return ResponseEntity.ok(userService.updateStatus(id, status));
    }
}
