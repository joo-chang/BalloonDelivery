package com.sparta.balloondelivery.restaurant.service;

import com.sparta.balloondelivery.data.entity.Restaurant;
import com.sparta.balloondelivery.data.repository.RestaurantRepository;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantSearchService {

    private final RestaurantRepository restaurantRepository;

    public List<RestaurantInfoResponse> searchRestaurants(String name, UUID categoryId, UUID locationId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Restaurant> restaurantPage;

        if (name != null && categoryId != null && locationId != null) {
            restaurantPage = restaurantRepository.searchByNameCategoryAndLocation(name, categoryId, locationId, pageRequest);
        } else if (name != null && categoryId != null) {
            restaurantPage = restaurantRepository.searchByNameAndCategory(name, categoryId, pageRequest);
        } else if (name != null && locationId != null) {
            restaurantPage = restaurantRepository.searchByNameAndLocation(name, locationId, pageRequest);
        } else if (categoryId != null && locationId != null) {
            restaurantPage = restaurantRepository.searchByCategoryAndLocation(categoryId, locationId, pageRequest);
        } else if (name != null) {
            restaurantPage = restaurantRepository.searchByName(name, pageRequest);
        } else if (categoryId != null) {
            restaurantPage = restaurantRepository.searchByCategory(categoryId, pageRequest);
        } else if (locationId != null) {
            restaurantPage = restaurantRepository.searchByLocation(locationId, pageRequest);
        } else {
            restaurantPage = restaurantRepository.findAll(pageRequest);
        }

        return restaurantPage.getContent().stream()
                .map(RestaurantInfoResponse::toDto)
                .collect(Collectors.toList());
    }
}
