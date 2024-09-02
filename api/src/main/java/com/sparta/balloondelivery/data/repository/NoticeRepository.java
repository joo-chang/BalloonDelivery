package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("select n from p_notices n where n.id = :noticeId and n.deletedYn = false")
    Optional<Notice> findById(UUID noticeId);

    @Query("select n from p_notices n where n.deletedYn = false order by n.updatedAt desc, n.createdAt desc")
    Page<Notice> findAll(Pageable pageable);
}
