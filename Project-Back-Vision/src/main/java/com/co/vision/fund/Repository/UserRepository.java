package com.co.vision.fund.Repository;

import com.co.vision.fund.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}
