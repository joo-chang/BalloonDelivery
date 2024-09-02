package com.sparta.balloondelivery.notice.service;

import com.sparta.balloondelivery.data.entity.Notice;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.repository.NoticeRepository;
import com.sparta.balloondelivery.data.repository.UserRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.notice.dto.NoticeReqDto;
import com.sparta.balloondelivery.notice.dto.NoticeResDto;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public NoticeService(NoticeRepository noticeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
    }

    public void createNotice(String userId, NoticeReqDto noticeReqDto) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
        Notice notice = new Notice(noticeReqDto.getTitle(), noticeReqDto.getContent(), user);
        noticeRepository.save(notice);
    }

    public Page<NoticeResDto> getAllNotice(Pageable pageable) {
        try {
            return noticeRepository.findAll(pageable).map(NoticeResDto::new);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }


    public NoticeResDto getNotice(UUID noticeId) {
        try {
            return new NoticeResDto(noticeRepository.findById(noticeId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER)));
        } catch (Exception e) {

            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public void deleteNotice(UUID noticeId, String userName) {
        try {
            Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            notice.setDeletedYnTrue(userName);
            noticeRepository.save(notice);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public void updateNotice(UUID noticeId, String userName, NoticeReqDto noticeReqDto) {
        try {
            Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new BaseException(ErrorCode.INVALID_PARAMETER));
            if(noticeReqDto.getTitle() != null) {
                notice.setTitle(noticeReqDto.getTitle());
            }
            if(noticeReqDto.getContent() != null) {
                notice.setContent(noticeReqDto.getContent());
            }
            notice.setUpdatedBy(userName);
            noticeRepository.save(notice);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }
}
