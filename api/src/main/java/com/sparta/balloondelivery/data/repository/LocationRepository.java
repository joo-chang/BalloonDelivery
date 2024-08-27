package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
}
