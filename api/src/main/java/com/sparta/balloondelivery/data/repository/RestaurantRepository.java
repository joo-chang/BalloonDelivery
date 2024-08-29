package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Menu;
import com.sparta.balloondelivery.data.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    Page<Restaurant> findByUser_UserId(UUID userId, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name%")
    Page<Restaurant> searchByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.category.categoryId = :categoryId")
    Page<Restaurant> searchByCategory(@Param("categoryId") UUID categoryId, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.location.locationId = :locationId")
    Page<Restaurant> searchByLocation(@Param("locationId") UUID locationId, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name% AND r.category.categoryId = :categoryId")
    Page<Restaurant> searchByNameAndCategory(@Param("name") String name, @Param("categoryId") UUID categoryId, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name% AND r.location.locationId = :locationId")
    Page<Restaurant> searchByNameAndLocation(@Param("name") String name, @Param("locationId") UUID locationId, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.category.categoryId = :categoryId AND r.location.locationId = :locationId")
    Page<Restaurant> searchByCategoryAndLocation(@Param("categoryId") UUID categoryId, @Param("locationId") UUID locationId, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name% AND r.category.categoryId = :categoryId AND r.location.locationId = :locationId")
    Page<Restaurant> searchByNameCategoryAndLocation(@Param("name") String name, @Param("categoryId") UUID categoryId, @Param("locationId") UUID locationId, Pageable pageable);
}
