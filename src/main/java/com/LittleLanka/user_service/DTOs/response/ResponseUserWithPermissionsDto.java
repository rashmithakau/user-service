package com.LittleLanka.user_service.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserWithPermissionsDto {
    private Long userId;
    private String userName;
    private List<String> permissions;
}