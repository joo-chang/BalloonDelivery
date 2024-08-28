package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "p_menus")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {
    // id 이름은 menu_id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_id")
    private UUID id;
    private String name;
    private Integer price;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
