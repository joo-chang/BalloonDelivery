package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    // 이름으로 검색 (부분 일치)
    @Query("SELECT m FROM Menu m WHERE m.name LIKE %:name%")
    Page<Menu> searchByName(@Param("name") String name, Pageable pageable);
}
