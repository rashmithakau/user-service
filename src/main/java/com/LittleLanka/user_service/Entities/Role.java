package com.LittleLanka.user_service.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false, unique = true, length = 20)
    private String roleName;

    @Column(columnDefinition = "json")
    private String permissions;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}