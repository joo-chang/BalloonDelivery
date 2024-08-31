package com.sparta.balloondelivery.notice.service;

import com.sparta.balloondelivery.data.entity.Notice;
import com.sparta.balloondelivery.data.repository.NoticeRepository;
import com.sparta.balloondelivery.notice.dto.NoticeReqDto;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public void createNotice(String userName, NoticeReqDto noticeReqDto) {
        Notice notice = new Notice(noticeReqDto.getTitle(), noticeReqDto.getContent());
        notice.setCreatedBy(userName);
        noticeRepository.save(notice);
    }
}
