package com.LittleLanka.user_service.DTOs;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private String password; // For registration purposes
    private Set<RoleDTO> roles;
    private String status;
}