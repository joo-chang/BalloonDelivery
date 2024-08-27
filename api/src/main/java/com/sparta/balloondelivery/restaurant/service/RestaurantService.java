package com.sparta.balloondelivery.restaurant.service;

import com.sparta.balloondelivery.data.entity.Category;
import com.sparta.balloondelivery.data.entity.Location;
import com.sparta.balloondelivery.data.entity.Restaurant;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.repository.CategoryRepository;
import com.sparta.balloondelivery.data.repository.LocationRepository;
import com.sparta.balloondelivery.data.repository.RestaurantRepository;
import com.sparta.balloondelivery.data.repository.UserRepository;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantCreateRequest;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public RestaurantCreateResponse createRestaurant(UUID userId, RestaurantCreateRequest request) {

        User user = userRepository.findById(userId) // 헤더에서 받은 userId를 사용해 유저 조회
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setContent(request.getContent());
        restaurant.setPhone(request.getPhone());
        restaurant.setUser(user);
        restaurant.setCategory(category);
        restaurant.setLocation(location);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return new RestaurantCreateResponse(
                savedRestaurant.getRestaurantId(),
                savedRestaurant.getName(),
                savedRestaurant.getContent(),
                savedRestaurant.getPhone(),
                savedRestaurant.getUser().getUserId(),
                savedRestaurant.getCategory().getCategoryId(),
                savedRestaurant.getLocation().getLocationId()
        );
    }
}
