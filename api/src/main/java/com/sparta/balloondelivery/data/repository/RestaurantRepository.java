package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    // 레스토랑 이름으로 검색 (부분 일치)
    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name%")
    Page<Restaurant> searchByName(@Param("name") String name, Pageable pageable);
}
