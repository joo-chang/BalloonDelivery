package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Notice;
import com.sparta.balloondelivery.notice.dto.NoticeResDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findById(UUID noticeId);
}
