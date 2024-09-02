package com.sparta.balloondelivery.menu.dto.response;

import com.sparta.balloondelivery.data.entity.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class MenuDeletedResponse {
    private UUID id;
    private String name;
    private boolean isDeleted;

    public static MenuDeletedResponse toDto(Menu menu) {
        return MenuDeletedResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .isDeleted(menu.isDeleted())
                .build();
    }
}
