package com.sparta.balloondelivery.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupReqDto {
    private String email;
    private String username;
    private String password;
    private String role;

}
