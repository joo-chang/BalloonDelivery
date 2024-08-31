package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    @Query("SELECT m FROM Menu m WHERE m.name LIKE %:name%")
    Page<Menu> searchByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId")
    Page<Menu> searchByRestaurant(@Param("restaurantId") UUID restaurantId, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.price BETWEEN :minPrice AND :maxPrice")
    Page<Menu> searchByPriceRange(@Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.name LIKE %:name% AND m.restaurant.id = :restaurantId")
    Page<Menu> searchByNameAndRestaurant(@Param("name") String name, @Param("restaurantId") UUID restaurantId, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.name LIKE %:name% AND m.price BETWEEN :minPrice AND :maxPrice")
    Page<Menu> searchByNameAndPrice(@Param("name") String name, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId AND m.price BETWEEN :minPrice AND :maxPrice")
    Page<Menu> searchByRestaurantAndPrice(@Param("restaurantId") UUID restaurantId, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice, Pageable pageable);

    @Query("SELECT m FROM Menu m WHERE m.name LIKE %:name% AND m.restaurant.id = :restaurantId AND m.price BETWEEN :minPrice AND :maxPrice")
    Page<Menu> searchByNameRestaurantAndPrice(@Param("name") String name, @Param("restaurantId") UUID restaurantId, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice, Pageable pageable);
}
