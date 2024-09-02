package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.AILog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AILogRepository extends JpaRepository<AILog, UUID> {
}
