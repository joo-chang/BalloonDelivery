package com.sparta.balloondelivery.menu.controller;

import com.sparta.balloondelivery.menu.dto.MenuRequestDto;
import com.sparta.balloondelivery.menu.service.MenuService;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ApiResponse<MenuRequestDto> createMenu(@RequestBody MenuRequestDto menuRequestDto) {
        menuService.createMenu(menuRequestDto);
        return ApiResponse.success("", menuRequestDto, "메뉴 생성에 성공했습니다.");
    }
}
