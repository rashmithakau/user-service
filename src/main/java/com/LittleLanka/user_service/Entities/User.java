package com.LittleLanka.user_service.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 10)
    private String status; // e.g., ACTIVE or INACTIVE

    // Add the roles property
    @ManyToMany
    @JoinTable(
            name = "user_roles", // Join table name
            joinColumns = @JoinColumn(name = "user_id"), // Foreign key in join table for User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Foreign key in join table for Role
    )
    private Set<Role> roles; // Many-to-Many relationship with Role
}