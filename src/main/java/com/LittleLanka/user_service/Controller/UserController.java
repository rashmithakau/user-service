package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // Enables Cross-Origin Resource Sharing
@RequestMapping("api/v1/user") // Base path for all user-related endpoints
public class UserController {

    @Autowired
    private UserService userService; // Inject UserService dependency

    // Endpoint to save a user using RequestSaveUserDTO
    @PostMapping("/save-user")
    public ResponseEntity<UserDTO> saveUser(@RequestBody RequestSaveUserDTO requestSaveUserDTO) {
        UserDTO userDTO = userService.saveUser(requestSaveUserDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED); // Return status 201
    }

    // Endpoint to get all users
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK); // Return status 200
    }
}