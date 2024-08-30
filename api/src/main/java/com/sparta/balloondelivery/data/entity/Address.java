package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity(name = "p_addresses")
@Table(name = "p_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "address_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_id")
    @JoinColumn(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 10)
    private String address1;  // 우편번호

    @Column(nullable = false, length = 255)
    private String address2;  // 주소

    @Column(nullable = false, length = 255)
    private String address3;  // 상세 주소

    public Address(long userId, String address1, String address2, String address3) {
        this.userId = userId;
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