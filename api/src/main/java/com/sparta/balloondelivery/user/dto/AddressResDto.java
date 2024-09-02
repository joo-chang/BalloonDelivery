package com.sparta.balloondelivery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResDto {
    private UUID addressId;
    private String address1;
    private String address2;
    private String address3;
}
