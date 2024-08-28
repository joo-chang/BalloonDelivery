package com.sparta.balloondelivery.restaurant.service;

import com.sparta.balloondelivery.data.entity.*;
import com.sparta.balloondelivery.data.repository.*;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantCreateRequest;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantUpdateRequest;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantCreateResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantInfoResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantUpdateResponse;
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
        restaurant.setAddress(savedAddress);  // 반드시 Address 필드를 설정

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantCreateResponse.builder()
                .restaurantId(savedRestaurant.getRestaurantId())
                .name(savedRestaurant.getName())
                .content(savedRestaurant.getContent())
                .phone(savedRestaurant.getPhone())
                .userId(savedRestaurant.getUser().getUserId())
                .categoryId(savedRestaurant.getCategory().getCategoryId())
                .locationId(savedRestaurant.getLocation().getLocationId())
                .addressId(savedRestaurant.getAddress().getAddressId())
                .build();
    }

    /**
     * 가게 전체 조회
     * @param restaurantId
     * @return
     */
    public RestaurantInfoResponse getRestaurantInfo(UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        return RestaurantInfoResponse.toDto(restaurant);
    }

    /**
     * 가게 정보 수정
     * @param restaurantId
     * @param request
     * @return
     */
    public RestaurantUpdateResponse updateRestaurant(UUID restaurantId, RestaurantUpdateRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        // 이름 수정 (없으면 기존 값 유지)
        restaurant.setName(request.getName().orElse(restaurant.getName()));

        // 내용 수정 (없으면 기존 값 유지)
        restaurant.setContent(request.getContent().orElse(restaurant.getContent()));

        // 전화번호 수정 (없으면 기존 값 유지)
        restaurant.setPhone(request.getPhone().orElse(restaurant.getPhone()));

        // 카테고리 수정 (없으면 기존 값 유지)
        request.getCategoryId().ifPresent(categoryId -> {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
            restaurant.setCategory(category);
        });

        // 위치 수정 (없으면 기존 값 유지)
        request.getLocationId().ifPresent(locationId -> {
            Location location = locationRepository.findById(locationId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));
            restaurant.setLocation(location);
        });

        // 주소 수정 (없으면 기존 값 유지)
        Address address = restaurant.getAddress();
        address.setAddress1(request.getAddress1().orElse(address.getAddress1()));
        address.setAddress2(request.getAddress2().orElse(address.getAddress2()));
        address.setAddress3(request.getAddress3().orElse(address.getAddress3()));
        Address savedAddress = addressRepository.save(address);

        restaurant.setAddress(savedAddress);

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantUpdateResponse.builder()
                .restaurantId(updatedRestaurant.getRestaurantId())
                .name(updatedRestaurant.getName())
                .content(updatedRestaurant.getContent())
                .phone(updatedRestaurant.getPhone())
                .categoryId(updatedRestaurant.getCategory().getCategoryId())
                .locationId(updatedRestaurant.getLocation().getLocationId())
                .addressId(updatedRestaurant.getAddress().getAddressId())
                .build();
    }
}
