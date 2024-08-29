package com.sparta.balloondelivery.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "p_addresses")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @Column(name = "address_id")
    private UUID id;
    private String address1; // 우편번호
    private String address2; // 주소
    private String address3; // 상세주소


    public Address(String address1, String address2, String address3) {
        this.id = UUID.randomUUID();
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public void setAddress(String address1, String address2, String address3) {
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }
}
