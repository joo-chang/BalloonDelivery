package com.sparta.balloondelivery.repository;

import com.sparta.balloondelivery.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
