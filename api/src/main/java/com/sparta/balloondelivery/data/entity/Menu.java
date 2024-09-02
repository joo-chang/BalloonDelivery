package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.util.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "p_menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID menuId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "content", length = 255)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "visiable", nullable = false)
    private Visiable visiable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
