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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 10)
    private String address1;  // 우편번호

    @Column(nullable = false, length = 255)
    private String address2;  // 주소

    @Column(nullable = false, length = 255)
    private String address3;  // 상세 주소

    public Address(String address1, String address2, String address3) {
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public Address(User user, String address1, String address2, String address3) {
        this.user = user;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public void setAddress(String address1, String address2, String address3, String username) {
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.setUpdatedBy(username);
    }
}
