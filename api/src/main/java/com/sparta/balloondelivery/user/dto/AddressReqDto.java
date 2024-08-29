package com.sparta.balloondelivery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressReqDto {
    private String address1;
    private String address2;
    private String address3;
}
