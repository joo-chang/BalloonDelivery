package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
