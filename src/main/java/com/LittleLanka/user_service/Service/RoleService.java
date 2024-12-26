package com.LittleLanka.user_service.Service;

import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveRoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO saveRole(RequestSaveRoleDTO requestSaveRoleDTO); // Save a role

}