package com.LittleLanka.user_service.DTOs.request;

import com.LittleLanka.user_service.Entities.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestSaveUserDTO {
    private String userName;
    private String phoneNumber;
    private String password;
    private UserStatus userStatus;
    private Long roleId;
}