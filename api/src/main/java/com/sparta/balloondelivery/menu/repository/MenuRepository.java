package com.sparta.balloondelivery.menu.repository;

import com.sparta.balloondelivery.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
