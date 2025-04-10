package com.LittleLanka.user_service.Entities;

import com.LittleLanka.user_service.Entities.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 40)
    private String userName;

    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 10)
    private UserStatus status; // ACTIVE or INACTIVE

    @Column
    private  Long outletID;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

}