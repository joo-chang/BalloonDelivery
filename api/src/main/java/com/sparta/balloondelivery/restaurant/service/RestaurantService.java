package com.sparta.balloondelivery.restaurant.service;

import com.sparta.balloondelivery.data.entity.*;
import com.sparta.balloondelivery.data.repository.*;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantCreateRequest;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantCreateResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantInfoResponse;
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
    private final AddressRepository addressRepository;

    /**
     * 가게 등록
     * @param userId
     * @param request
     * @return
     */
    public RestaurantCreateResponse createRestaurant(UUID userId, RestaurantCreateRequest request) {

        User user = userRepository.findById(userId) // 헤더에서 받은 userId를 사용해 유저 조회
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        Address address = new Address();
        address.setAddress1(request.getAddress1());
        address.setAddress2(request.getAddress2());
        address.setAddress3(request.getAddress3());

        Address savedAddress = addressRepository.save(address);

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
                savedRestaurant.getLocation().getLocationId(),
                savedAddress.getAddressId()
        );
    }

    /**
     * 가게 조회
     * @param restaurantId
     * @return
     */
    public RestaurantInfoResponse getRestaurantInfo(UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        return new RestaurantInfoResponse(
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getContent(),
                restaurant.getPhone(),
                restaurant.getUser().getUserId(),
                restaurant.getCategory().getCategoryId(),
                restaurant.getLocation().getLocationId(),
                restaurant.getAddress().getAddressId()
        );
    }
}
