package com.LittleLanka.user_service.Service.impl;

import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveRoleDTO;
import com.LittleLanka.user_service.Entities.Role;
import com.LittleLanka.user_service.Repositories.RoleRepository;
import com.LittleLanka.user_service.Service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceIMPL implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDTO saveRole(RequestSaveRoleDTO requestSaveRoleDTO) {
        // Map RequestSaveRoleDTO to Role entity
        Role role = modelMapper.map(requestSaveRoleDTO, Role.class);
        role = roleRepository.save(role); // Save the role entity to the database
        return modelMapper.map(role, RoleDTO.class); // Map the saved entity to RoleDTO
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