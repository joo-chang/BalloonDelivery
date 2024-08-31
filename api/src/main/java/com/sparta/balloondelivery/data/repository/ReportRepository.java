package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT r FROM p_reports r WHERE r.id = :reportId ORDER BY r.createdAt DESC")
    Optional<Report> findById(UUID reportId);

    @Query("SELECT r FROM p_reports r WHERE r.userId = :userId ORDER BY r.createdAt ASC")
    Page<Report> findByUserId(long userId, Pageable pageable);
}
