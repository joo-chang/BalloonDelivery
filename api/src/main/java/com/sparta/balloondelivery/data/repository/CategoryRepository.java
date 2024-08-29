package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
