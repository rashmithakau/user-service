package com.LittleLanka.user_service.Service.impl;

import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveRoleDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseRoleDto;
import com.LittleLanka.user_service.Entities.Permission;
import com.LittleLanka.user_service.Entities.Role;
import com.LittleLanka.user_service.Repositories.PermissionRepository;
import com.LittleLanka.user_service.Repositories.RoleRepository;
import com.LittleLanka.user_service.Service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceIMPL implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public ResponseRoleDto saveRole(RequestSaveRoleDTO requestSaveRoleDTO) {
        List<Permission> permissionList=permissionRepository.findAllById(requestSaveRoleDTO.getPermissionIds());
        Role role = modelMapper.map(requestSaveRoleDTO, Role.class);
        role.setPermissions(permissionList);
        Role roleSaved = roleRepository.save(role);

        List<String> permisionStringList=new ArrayList<>();

        for(Permission permission:permissionList){
            permisionStringList.add(permission.getPermissionName());
        }

        ResponseRoleDto responseRoleDto =new ResponseRoleDto();
        responseRoleDto.setRoleId(roleSaved.getRoleId());
        responseRoleDto.setRoleName(roleSaved.getRoleName());
        responseRoleDto.setPermissions(permisionStringList);
        return responseRoleDto;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> allRoles = roleRepository.findAll();
        if (allRoles.isEmpty()) {
            throw new RuntimeException("No roles found");
        }
        return allRoles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }
}