package com.sparta.balloondelivery.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressReqDto {
    @NotBlank
    private String address1;
    @NotBlank
    private String address2;
    @NotBlank
    private String address3;
}
