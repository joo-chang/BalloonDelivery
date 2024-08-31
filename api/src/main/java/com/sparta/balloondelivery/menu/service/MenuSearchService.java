package com.sparta.balloondelivery.menu.service;

import com.sparta.balloondelivery.data.entity.Menu;
import com.sparta.balloondelivery.data.repository.MenuRepository;
import com.sparta.balloondelivery.menu.dto.response.MenuInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuSearchService {

    private final MenuRepository menuRepository;

    public List<MenuInfoResponse> searchMenus(String name, UUID restaurantId, Integer minPrice, Integer maxPrice, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Menu> menuPage;

        if (name != null && restaurantId != null && minPrice != null && maxPrice != null) {
            menuPage = menuRepository.searchByNameRestaurantAndPrice(name, restaurantId, minPrice, maxPrice, pageRequest);
        } else if (name != null && restaurantId != null) {
            menuPage = menuRepository.searchByNameAndRestaurant(name, restaurantId, pageRequest);
        } else if (name != null && minPrice != null && maxPrice != null) {
            menuPage = menuRepository.searchByNameAndPrice(name, minPrice, maxPrice, pageRequest);
        } else if (restaurantId != null && minPrice != null && maxPrice != null) {
            menuPage = menuRepository.searchByRestaurantAndPrice(restaurantId, minPrice, maxPrice, pageRequest);
        } else if (name != null) {
            menuPage = menuRepository.searchByName(name, pageRequest);
        } else if (restaurantId != null) {
            menuPage = menuRepository.searchByRestaurant(restaurantId, pageRequest);
        } else if (minPrice != null && maxPrice != null) {
            menuPage = menuRepository.searchByPriceRange(minPrice, maxPrice, pageRequest);
        } else {
            menuPage = menuRepository.findAll(pageRequest);
        }

        return menuPage.getContent().stream()
                .map(MenuInfoResponse::toDto)
                .collect(Collectors.toList());
    }
}
