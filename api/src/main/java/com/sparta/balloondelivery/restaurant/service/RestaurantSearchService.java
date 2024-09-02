package com.sparta.balloondelivery.restaurant.service;

import com.sparta.balloondelivery.data.entity.Restaurant;
import com.sparta.balloondelivery.data.repository.RestaurantRepository;
import com.sparta.balloondelivery.restaurant.dto.response.RestaurantInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantSearchService {

    private final RestaurantRepository restaurantRepository;

    /**
     * 레스토랑을 검색하고 정렬하는 메서드.
     *
     * @param name   레스토랑 이름으로 검색 (부분 일치)
     * @param page   페이지 번호
     * @param size   페이지 크기 (10, 30, 50)
     * @param sortBy 정렬 기준 (생성일순: "createdAt", 수정일순: "updatedAt")
     * @return 검색된 레스토랑 리스트
     */
    public List<RestaurantInfoResponse> searchRestaurants(String name, int page, int size, String sortBy) {
        // 페이지당 노출 건수 제한
        if (size != 10 && size != 30 && size != 50) {
            size = 10; // 기본값 10건
        }

        // 정렬 조건 설정
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));

        // 레스토랑 이름으로 검색
        Page<Restaurant> restaurantPage = restaurantRepository.searchByName(name, pageRequest);

        // 검색 결과를 DTO로 변환하여 반환
        return restaurantPage.getContent().stream()
                .map(RestaurantInfoResponse::toDto)
                .collect(Collectors.toList());
    }
}
