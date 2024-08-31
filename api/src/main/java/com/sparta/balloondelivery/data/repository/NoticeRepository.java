package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
