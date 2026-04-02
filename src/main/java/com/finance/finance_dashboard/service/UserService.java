package com.finance.finance_dashboard.service;

import com.finance.finance_dashboard.Repository.UserRepository;
import com.finance.finance_dashboard.entity.Role;
import com.finance.finance_dashboard.entity.Status;
import com.finance.finance_dashboard.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }

    public User updateRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User updateStatus(Long userId, Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        return userRepository.save(user);
    }
}