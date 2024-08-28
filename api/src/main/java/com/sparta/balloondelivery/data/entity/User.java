package com.sparta.balloondelivery.data.entity;

import com.sparta.balloondelivery.user.dto.UserReqDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "p_users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private boolean deletedYN;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public User(String email, String username, String password, UserRole role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
