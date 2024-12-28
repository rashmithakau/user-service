package com.LittleLanka.user_service.Service.impl;

import com.LittleLanka.user_service.DTOs.PermissionDto;
import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.request.RequestPermissionDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveRoleDTO;
import com.LittleLanka.user_service.Entities.Permission;
import com.LittleLanka.user_service.Repositories.PermissionRepository;
import com.LittleLanka.user_service.Service.PermissionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private  PermissionRepository permissionRepository;
    private  ModelMapper modelMapper;

    @Override
    public PermissionDto saveRole(RequestPermissionDto requestPermissionDto) {
        boolean isExsits =permissionRepository.existsByPermissionName(requestPermissionDto.getPermissionName());
        if(!isExsits){
            Permission permission = modelMapper.map(requestPermissionDto, Permission.class);
            Permission permission1=permissionRepository.save(permission);
            return modelMapper.map(permission1,PermissionDto.class);
        }
        return null;
    }
}
