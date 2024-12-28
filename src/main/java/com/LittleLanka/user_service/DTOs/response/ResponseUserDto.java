package com.LittleLanka.user_service.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDto {
    private Long userId;
    private String userName;
    private String phoneNumber;
    private String status;
    private Long roleId;
}
