package com.co.vision.fund.controllers;

import com.co.vision.fund.dtos.LogIn;
import com.co.vision.fund.entity.Users;
import com.co.vision.fund.services.UsersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("visionfund/api/users")
public class UsersController {

    private final UsersService service;

    @GetMapping("/get/all")
    public List<Users> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/by/email/{email}")
    public Users getByEmail(@PathVariable String email) {
        return service.getByEmail(email);
    }

    @PostMapping
    public Users create(@RequestBody Users user) {
        return service.create(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> update(@PathVariable Long id, @RequestBody Users data) {
        return service.update(id,data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogIn request) {
        return service.login(request);
    }
}
