package com.co.vision.fund.services.impl;

import com.co.vision.fund.Repository.RecordsRepository;
import com.co.vision.fund.Repository.UserRepository;
import com.co.vision.fund.entity.Records;
import com.co.vision.fund.entity.Users;
import com.co.vision.fund.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceRecordsImpl {
    private final RecordsRepository recordsRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(String email, String password) {
        Records user = recordsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public String adminLogin(String email, String password) {
        Users user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
