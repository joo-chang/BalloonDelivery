package com.sparta.balloondelivery.menu.service;

import com.sparta.balloondelivery.data.entity.Menu;
import com.sparta.balloondelivery.data.entity.Restaurant;
import com.sparta.balloondelivery.data.entity.Visiable;
import com.sparta.balloondelivery.data.repository.MenuRepository;
import com.sparta.balloondelivery.data.repository.RestaurantRepository;
import com.sparta.balloondelivery.menu.dto.request.MenuCreateRequest;
import com.sparta.balloondelivery.menu.dto.request.MenuUpdateRequest;
import com.sparta.balloondelivery.menu.dto.response.MenuCreateResponse;
import com.sparta.balloondelivery.menu.dto.response.MenuInfoResponse;
import com.sparta.balloondelivery.menu.dto.response.MenuUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    /**
     * 메뉴 생성
     *
     * @param request
     * @return
     */
    @Transactional
    public MenuCreateResponse createMenu(MenuCreateRequest request) {

        // 메뉴를 생성하기 위해 필요한 레스토랑을 가져옴
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        // 메뉴 객체 생성
        Menu menu = new Menu();
        menu.setName(request.getName());
        menu.setPrice(request.getPrice());
        menu.setContent(request.getContent());
        menu.setVisiable(Visiable.판매중);
        menu.setRestaurant(restaurant);

        // 데이터베이스에 메뉴 저장
        Menu savedMenu = menuRepository.save(menu);


        // MenuCreateResponse를 반환
        return MenuCreateResponse.toDto(menu);
    }

    /**
     * 메뉴 수정
     *
     * @param id
     * @param request
     * @return
     */
    @Transactional
    public MenuUpdateResponse updateMenu(UUID id, MenuUpdateRequest request) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu ID"));

        // 이름, 가격, 내용 수정 (없으면 기존 값 유지)
        menu.setName(request.getName().orElse(menu.getName()));
        menu.setPrice(request.getPrice().orElse(menu.getPrice()));
        menu.setContent(request.getContent().orElse(menu.getContent()));


        Menu updatedMenu = menuRepository.save(menu);

        return MenuUpdateResponse.toDto(updatedMenu);
    }

    /**
     * 메뉴 단건 조회
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public MenuInfoResponse getMenuInfo(UUID id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu ID"));

        return MenuInfoResponse.toDto(menu);
    }

    /**
     * 메뉴 전체 조회 (페이지네이션 포함)
     *
     * @param page
     * @param size
     * @return
     */
    @Transactional(readOnly = true)
    public List<MenuInfoResponse> getAllMenus(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Menu> menuPage = menuRepository.findAll(pageRequest);

        return menuPage.stream()
                .map(MenuInfoResponse::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 메뉴 상태 변경
     *
     * @param id
     * @param status
     * @return
     */
    @Transactional
    public MenuUpdateResponse updateMenuStatus(UUID id, Visiable status) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu ID"));

        menu.setVisiable(status);

        Menu updatedMenu = menuRepository.save(menu);

        return MenuUpdateResponse.toDto(updatedMenu);
    }


}
