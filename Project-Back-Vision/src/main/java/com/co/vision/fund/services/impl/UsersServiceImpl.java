package com.co.vision.fund.services.impl;

import com.co.vision.fund.Repository.UserRepository;
import com.co.vision.fund.entity.Users;
import com.co.vision.fund.services.UsersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Users> getAll() {
        return repository.findAll();
    }

    @Override
    public Users getByEmail(final String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Users create(final Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public ResponseEntity<Users> update(final Long id, final Users data) {
        return repository.findById(id)
            .map(user -> {
                user.setName(data.getName());
                user.setPassword(passwordEncoder.encode(data.getPassword()));
                return ResponseEntity.ok(repository.save(user));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> delete(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
