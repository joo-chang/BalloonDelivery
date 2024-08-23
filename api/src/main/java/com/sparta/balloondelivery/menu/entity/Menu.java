package com.sparta.balloondelivery.menu.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity(name = "p_menus")
@Getter
public class Menu {
    @Id
    private UUID id;
    private String name;
    private Integer price;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
