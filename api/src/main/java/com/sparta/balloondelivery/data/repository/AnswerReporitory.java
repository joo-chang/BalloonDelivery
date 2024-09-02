package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnswerReporitory extends JpaRepository<Answer, Long> {

    Optional<Answer> findByReportId(UUID reportId);
}
