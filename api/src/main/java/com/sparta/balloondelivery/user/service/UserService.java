package com.sparta.balloondelivery.user.service;

import com.sparta.balloondelivery.auth.service.AuthService;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.entity.UserRole;
import com.sparta.balloondelivery.data.repository.UserRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.user.dto.RoleUpdateReqDto;
import com.sparta.balloondelivery.user.dto.UserReqDto;
import com.sparta.balloondelivery.user.dto.UserResDto;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private static final String USER_ROLE =  "userRole";
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    public UserResDto getUser(String userId, Long targetUserId) {
        if (!userId.equals(targetUserId.toString()) && !authService.hasPermission(Long.parseLong(userId)).equals(UserRole.MANAGER)) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
        UserResDto userResDto = new UserResDto();
        userRepository.findById(targetUserId).ifPresentOrElse(
                user -> {
                    userResDto.setName(user.getUsername());
                    userResDto.setEmail(user.getEmail());
                    userResDto.setRole(user.getRole());
                },
                () -> {
                    throw new BaseException(ErrorCode.NOT_EXIST_USER);
                }
        );
        return userResDto;
    }

    public void updateUser(String userId, UserReqDto userReqDto) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_USER));
        if (userReqDto.getUsername() != null) {
            user.setUsername(userReqDto.getUsername());
        }
        if (userReqDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
        }

        // 사용자 정보 저장
        userRepository.save(user);
    }


    public void deleteUser(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_USER));
        user.setDeletedYN(true);
        userRepository.save(user);
    }

    public Page<UserResDto> getAllUsers(Long userId, Pageable pageable) {
        UserRole userRole = authService.hasPermission(userId);
        if (!userRole.equals(UserRole.MANAGER)) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }

        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(user -> {
            UserResDto userResDto = new UserResDto();
            userResDto.setName(user.getUsername());
            userResDto.setEmail(user.getEmail());
            userResDto.setRole(user.getRole());
            return userResDto;
        });
    }


    @CacheEvict(cacheNames = USER_ROLE, key = "#targetUserId")
    public void updateUserRole(String userId, Long targetUserId, RoleUpdateReqDto role) {
        UserRole userRole = authService.hasPermission(Long.parseLong(userId));
        if (!userRole.equals(UserRole.MANAGER)) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }

        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_USER));
        user.setRole(role.getRole());
        userRepository.save(user);
    }


}
