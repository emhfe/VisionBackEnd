package com.co.vision.fund.services;

import java.util.List;

import com.co.vision.fund.entity.Users;
import org.springframework.http.ResponseEntity;

public interface UsersService {
    List<Users> getAll();

    Users getByEmail(String email);

    Users create(Users user);

    ResponseEntity<Users> update(Long id, Users data);

    ResponseEntity<Void> delete(Long id);
}
