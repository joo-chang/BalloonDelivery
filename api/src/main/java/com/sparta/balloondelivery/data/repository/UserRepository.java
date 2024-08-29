package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.role FROM p_users u WHERE u.id = :userId")
    UserRole findRoleById(Long userId);
}
