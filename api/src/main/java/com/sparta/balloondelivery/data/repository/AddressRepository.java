package com.sparta.balloondelivery.data.repository;

import com.sparta.balloondelivery.data.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("select a from p_addresses a where a.user.id = :userId and a.deletedYn = false\n")
    Page<Address> findByUserId(long userId, Pageable pageable);
    @Query("select count(a) from p_addresses a where a.user.id = :userId and a.deletedYn = false")
    long countByUserId(long userId);

}
