package com.sparta.balloondelivery.user.dto;

import com.sparta.balloondelivery.data.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateReqDto {
    UserRole role;
}
