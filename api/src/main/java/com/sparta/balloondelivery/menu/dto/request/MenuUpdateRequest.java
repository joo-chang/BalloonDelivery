package com.sparta.balloondelivery.menu.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class MenuUpdateRequest {
    private Optional<String> name = Optional.empty();
    private Optional<Integer> price = Optional.empty();
    private Optional<String> content = Optional.empty();
}
