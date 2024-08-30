package com.sparta.balloondelivery.restaurant.service;

import com.sparta.balloondelivery.data.entity.*;
import com.sparta.balloondelivery.data.repository.*;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantCreateRequest;
import com.sparta.balloondelivery.restaurant.dto.request.RestaurantUpdateRequest;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantCreateResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantInfoResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantPageInfoResponse;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
     *
     * @param userId
     * @param request
     * @return
     */
    public RestaurantCreateResponse createRestaurant(Long userId, RestaurantCreateRequest request) {

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
        address.setUserId(userId);

        Address savedAddress = addressRepository.save(address);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setContent(request.getContent());
        restaurant.setPhone(request.getPhone());
        restaurant.setUser(user);
        restaurant.setCategory(category);
        restaurant.setLocation(location);
        restaurant.setAddress(savedAddress);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantCreateResponse.toDto(savedRestaurant);
    }

    /**
     * 가게 조회
     *
     * @param id
     * @return
     */
    public RestaurantInfoResponse getRestaurantInfo(UUID id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        return RestaurantInfoResponse.toDto(restaurant);
    }

    /**
     * 가게 정보 수정
     *
     * @param id
     * @param request
     * @return
     */
    public RestaurantUpdateResponse updateRestaurant(UUID id, RestaurantUpdateRequest request) {

        Restaurant restaurant = restaurantRepository.findById(id)
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
        address.setUserId(restaurant.getUser().getId());
        Address savedAddress = addressRepository.save(address);
        restaurant.setAddress(savedAddress);

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantUpdateResponse.toDto(updatedRestaurant);
    }

    /**
     * 가게 전체 조회
     *
     * @param page
     * @param size
     * @return
     */
    public RestaurantPageInfoResponse getAllRestaurantInfo(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Restaurant> restaurantPage = restaurantRepository.findAll(pageRequest);

        List<RestaurantInfoResponse> content = restaurantPage.getContent().stream()
                .map(RestaurantInfoResponse::toDto)
                .collect(Collectors.toList());

        return new RestaurantPageInfoResponse(
                content,
                page,
                size,
                restaurantPage.getTotalElements(),
                restaurantPage.getTotalPages()
        );
    }

    /**
     * 내 가게 조회
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public RestaurantPageInfoResponse getMyRestaurantInfo(Long userId, int page, int size) {
//        UUID userUUID = UUID.fromString(userId);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Restaurant> restaurantPage = restaurantRepository.findByUserId(userId, pageRequest);

        List<RestaurantInfoResponse> content = restaurantPage.getContent().stream()
                .map(RestaurantInfoResponse::toDto)
                .collect(Collectors.toList());

        return new RestaurantPageInfoResponse(
                content,
                page,
                size,
                restaurantPage.getTotalElements(),
                restaurantPage.getTotalPages()
        );
    }

    /**
     * 가게 숨김/표시 설정 및 변경된 정보 반환
     *
     * @param restaurantId
     * @return RestaurantInfoResponse
     */
    public RestaurantInfoResponse hideRestaurant(UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        // 현재 visible 상태를 반전
        boolean newVisibility = !restaurant.getVisible();
        restaurant.setVisible(newVisibility);

        // 변경된 레스토랑 정보를 저장
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        // 변경된 레스토랑 정보를 반환
        return RestaurantInfoResponse.toDto(updatedRestaurant);
    }


}
