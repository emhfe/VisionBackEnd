package com.co.vision.fund.services.impl;

import com.co.vision.fund.Repository.PasswordResetTokenRepository;
import com.co.vision.fund.Repository.RecordsRepository;
import com.co.vision.fund.entity.PasswordResetToken;
import com.co.vision.fund.entity.Records;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl {

    private final PasswordResetTokenRepository tokenRepository;
    private final RecordsRepository recordsRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    public String createResetToken(String email) {
        recordsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setExpirationDate(LocalDateTime.now().plusMinutes(10));

        tokenRepository.save(resetToken);

        String body = "Recovery link: https://www.visionfundlu.com/reset-password.html?token=" + token;
        String affair = "Recover password";

        sendEmail(body, affair, email);

        return token;
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token"));

        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("The token has expired");
        }

        Records user = recordsRepository.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(newPassword));
        recordsRepository.save(user);

        tokenRepository.delete(resetToken);
    }

    public void sendEmail(String body, String affair, String emailTo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(emailTo);
        mensaje.setSubject(affair);
        mensaje.setText(body);
        mensaje.setFrom(email);

        mailSender.send(mensaje);
    }
}
