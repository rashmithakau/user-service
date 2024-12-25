package com.LittleLanka.user_service.DTOs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String userName;
    private String phoneNumber;
    private String password;
    private String status;
}