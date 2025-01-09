package com.LittleLanka.user_service.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestSaveRoleDTO {
    private String roleName;
    private List<Long> permissionIds;
}
