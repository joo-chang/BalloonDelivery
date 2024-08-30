package com.sparta.balloondelivery.menu.controller;

import com.sparta.balloondelivery.data.entity.Visiable;
import com.sparta.balloondelivery.menu.dto.request.MenuCreateRequest;
import com.sparta.balloondelivery.menu.dto.request.MenuUpdateRequest;
import com.sparta.balloondelivery.menu.dto.response.MenuCreateResponse;
import com.sparta.balloondelivery.menu.dto.response.MenuInfoResponse;
import com.sparta.balloondelivery.menu.dto.response.MenuUpdateResponse;
import com.sparta.balloondelivery.menu.service.MenuService;
import com.sparta.balloondelivery.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 메뉴 생성 API
     */
    @PostMapping
    public ApiResponse<MenuCreateResponse> createMenu(
            @RequestBody MenuCreateRequest request
    ) {
        log.info("Menu creation request received");
        MenuCreateResponse response = menuService.createMenu(request);
        return ApiResponse.success("", response, "메뉴 등록 성공");
    }

    /**
     * 메뉴 수정 API
     */
    @PutMapping("/{menu_id}")
    public ApiResponse<MenuUpdateResponse> updateMenu(
            @PathVariable("menu_id") UUID id,
            @RequestBody MenuUpdateRequest request
    ) {
        MenuUpdateResponse response = menuService.updateMenu(id, request);
        return ApiResponse.success("", response, "메뉴 수정 성공");
    }

    /**
     * 메뉴 단건 조회 API
     */
    @GetMapping("/{menu_id}")
    public ApiResponse<MenuInfoResponse> getMenuInfo(@PathVariable("menu_id") UUID id) {
        MenuInfoResponse response = menuService.getMenuInfo(id);
        return ApiResponse.success("", response, "메뉴 조회 성공");
    }

    /**
     * 메뉴 전체 조회 API
     */
    @GetMapping
    public ApiResponse<List<MenuInfoResponse>> getAllMenus(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<MenuInfoResponse> response = menuService.getAllMenus(page, size);
        return ApiResponse.success("", response, "전체 메뉴 조회 성공");
    }

    /**
     * 메뉴 상태 변경 API
     */
    @PatchMapping("/{menu_id}/status")
    public ApiResponse<MenuUpdateResponse> updateMenuStatus(
            @PathVariable("menu_id") UUID id,
            @RequestParam("status") Visiable status
    ) {
        MenuUpdateResponse response = menuService.updateMenuStatus(id, status);
        return ApiResponse.success("", response, "메뉴 상태 변경 성공");
    }


}
