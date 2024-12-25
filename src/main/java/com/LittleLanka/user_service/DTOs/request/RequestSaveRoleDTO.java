package com.LittleLanka.user_service.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestSaveRoleDTO {
    private Long roleId;
    private String roleName;
   // private String permissions;
}
