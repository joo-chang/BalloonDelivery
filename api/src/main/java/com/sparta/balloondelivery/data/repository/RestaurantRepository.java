package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
}
