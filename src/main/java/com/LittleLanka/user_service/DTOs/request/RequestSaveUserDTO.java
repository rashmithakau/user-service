package com.LittleLanka.user_service.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestSaveUserDTO {
    private Long userId;
    private String userName;
    private String phoneNumber;
    private String password;
    private String status;
}