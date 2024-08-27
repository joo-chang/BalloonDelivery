package com.sparta.balloondelivery.auth.dto;

import com.sparta.balloondelivery.data.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupReqDto {

    @Email
    @NotBlank
    private String email;

    @Size(min = 4, max = 10, message = "username은 최소 4자 이상, 10자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "username은 알파벳 소문자(a~z)와 숫자(0~9)로만 구성되어야 합니다.")
    private String username;

    @Size(min = 8, max = 15, message = "password는 최소 8자 이상, 15자 이하이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@!$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$", message = "password는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 포함해야 합니다.")
    private String password;
    private UserRole role;
    private String adminToken = "";
}
