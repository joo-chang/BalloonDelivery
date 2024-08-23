package com.sparta.balloondelivery.menu.controller;

import com.sparta.balloondelivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
}
