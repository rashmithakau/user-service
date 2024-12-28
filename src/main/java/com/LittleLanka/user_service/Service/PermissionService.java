package com.LittleLanka.user_service.Service;

import com.LittleLanka.user_service.DTOs.PermissionDto;
import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.request.RequestPermissionDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveRoleDTO;

public interface PermissionService {
     PermissionDto saveRole(RequestPermissionDto requestPermissionDto);
}
