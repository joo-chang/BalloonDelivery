package com.sparta.balloondelivery.menu.controller;

import com.sparta.balloondelivery.menu.dto.MenuRequestDto;
import com.sparta.balloondelivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createMenu(@RequestBody MenuRequestDto menuRequestDto) {
        menuService.createMenu(menuRequestDto);
        return ResponseEntity.ok().build();
    }
}
