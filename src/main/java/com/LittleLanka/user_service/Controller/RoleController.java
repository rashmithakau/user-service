package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveRoleDTO;
import com.LittleLanka.user_service.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // Enables Cross-Origin Resource Sharing
@RequestMapping("api/v1/role") // Base path for all role-related endpoints
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Endpoint to save a role
    @PostMapping("/save-role")
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RequestSaveRoleDTO requestSaveRoleDTO) {
        RoleDTO roleDTO = roleService.saveRole(requestSaveRoleDTO);
        return new ResponseEntity<>(roleDTO, HttpStatus.CREATED); // Return status 201
    }

    // Endpoint to get all roles
    @GetMapping("/get-all-roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> allRoles = roleService.getAllRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK); // Return status 200
    }

}