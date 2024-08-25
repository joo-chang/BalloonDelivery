package com.sparta.balloondelivery.menu.service;

import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.menu.dto.MenuRequestDto;
import com.sparta.balloondelivery.repository.MenuRepository;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;


    @Transactional
    public void createMenu(MenuRequestDto menuRequestDto) {
        if(menuRequestDto.getName().equals("test")){
            throw new BaseException(ErrorCode.AUTHORIZATION_FAIL);
        }

    }
}
