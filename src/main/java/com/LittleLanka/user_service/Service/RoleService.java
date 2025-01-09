package com.LittleLanka.user_service.Service;

import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveRoleDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseRoleDto;

import java.util.List;

public interface RoleService {
    ResponseRoleDto saveRole(RequestSaveRoleDTO requestSaveRoleDTO); // Save a role
    List<RoleDTO> getAllRoles(); // Get all roles

}