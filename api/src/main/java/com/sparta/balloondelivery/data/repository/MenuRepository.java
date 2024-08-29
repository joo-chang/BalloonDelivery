package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
}
