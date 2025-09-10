package com.co.vision.fund.controllers;

import com.co.vision.fund.dtos.LogIn;
import com.co.vision.fund.dtos.LoginResponse;
import com.co.vision.fund.services.impl.AuthServiceRecordsImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("visionfund/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthServiceRecordsImpl authServiceRecords;

    @PostMapping("/records/login")
    public LoginResponse login(@RequestBody LogIn request) {
        String token = authServiceRecords.login(request.getEmail(), request.getPassword());
        return new LoginResponse(token);
    }

    @PostMapping("/admin/login")
    public LoginResponse adminLogin(@RequestBody LogIn request) {
        String token = authServiceRecords.adminLogin(request.getEmail(), request.getPassword());
        return new LoginResponse(token);
    }
}
