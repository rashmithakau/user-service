package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.PermissionDto;
import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.request.RequestPermissionDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveRoleDTO;
import com.LittleLanka.user_service.Service.PermissionService;
import com.LittleLanka.user_service.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin // Enables Cross-Origin Resource Sharing
@RequestMapping("api/v1/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    // Endpoint to save a role
    @PostMapping("/save")
    public ResponseEntity<PermissionDto> saveRole(@RequestBody RequestPermissionDto requestPermissionDto) {
        PermissionDto permissionDto = permissionService.saveRole(requestPermissionDto);
        return new ResponseEntity<>(permissionDto, HttpStatus.CREATED); // Return status  201
    }
}
