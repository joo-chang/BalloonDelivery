package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
