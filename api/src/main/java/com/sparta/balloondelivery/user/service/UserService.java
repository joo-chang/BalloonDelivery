package com.sparta.balloondelivery.user.service;

import com.sparta.balloondelivery.data.entity.Address;
import com.sparta.balloondelivery.data.entity.User;
import com.sparta.balloondelivery.data.repository.AddressRepository;
import com.sparta.balloondelivery.data.repository.UserRepository;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.user.dto.*;
import com.sparta.balloondelivery.util.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String USER_ROLE = "userRole";

    public UserService(UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResDto getUser(Long targetUserId) {
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
        userRepository.save(user);
    }


    public void deleteUser(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_USER));
        user.setDeletedYnTrue(user.getUsername());
        userRepository.save(user);
    }

    public Page<UserResDto> getAllUsers(Long userId, Pageable pageable) {
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
    public void updateUserRole(Long targetUserId, RoleUpdateReqDto role) {
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_USER));
        user.setRole(role.getRole());
        userRepository.save(user);
    }


    @Transactional
    public void addAddress(long userId, AddressReqDto addressReqDto) {
        try {
            if (addressRepository.countByUserId(userId) >= 10) {
                throw new BaseException(ErrorCode.ADDRESS_LIMIT);
            }
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));
            Address address = new Address(user, addressReqDto.getAddress1(), addressReqDto.getAddress2(), addressReqDto.getAddress3());
            addressRepository.save(address);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }


    public void updateAddress(long userId, UUID addressId, AddressReqDto addressReqDto, String userName) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));
            address.setAddress(addressReqDto.getAddress1(), addressReqDto.getAddress2(), addressReqDto.getAddress3(), user.getUsername());
            addressRepository.save(address);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public void deleteAddress(long userId, UUID addressId, String userName) {
        try {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));
            address.setDeletedYnTrue(userName);
            addressRepository.save(address);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public Page<AddressResDto> getAddress(long userId, Pageable pageable) {
        try {
            Page<Address> addressPage = addressRepository.findByUserId(userId, pageable);
            return addressPage.map(address -> new AddressResDto(address.getId(), address.getAddress1(), address.getAddress2(), address.getAddress3()));
        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }

    public AddressResDto getAddressById(long userId, UUID addressId) {
        try {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new BaseException(ErrorCode.ENTITY_NOT_FOUND));
            return new AddressResDto(address.getId(), address.getAddress1(), address.getAddress2(), address.getAddress3());

        } catch (Exception e) {
            throw new BaseException(ErrorCode.INVALID_PARAMETER);
        }
    }
}
