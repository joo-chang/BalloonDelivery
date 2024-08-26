package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
