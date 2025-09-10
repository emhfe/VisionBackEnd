package com.co.vision.fund.controllers;

import com.co.vision.fund.services.impl.PasswordResetServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("visionfund/api/users")
public class PasswordResetController {

    private final PasswordResetServiceImpl resetService;

    public PasswordResetController(PasswordResetServiceImpl resetService) {
        this.resetService = resetService;
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        resetService.createResetToken(email);
        return "A recovery link has been sent to the registered email.";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword) {
        resetService.resetPassword(token, newPassword);
        return "Password reset successfully";
    }
}
