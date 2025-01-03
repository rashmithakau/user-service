package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;
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
    public ResponseEntity<ResponseUserDto> saveUser(@RequestBody RequestSaveUserDTO requestSaveUserDTO) {
        ResponseUserDto responseUserDto = userService.saveUser(requestSaveUserDTO);
        return new ResponseEntity<>(responseUserDto, HttpStatus.CREATED); // Return status 201
    }

    // Endpoint to get all users
    @GetMapping("/get-all-users")
    public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
        List<ResponseUserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK); // Return status 200
    }

    @PutMapping("/update-password/{userId}")
    public ResponseEntity<String> updatePassword(
            @PathVariable Long userId,
            @RequestBody String newPassword
    ) {
        userService.updatePassword(userId, newPassword);
        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable Long userId) {
        ResponseUserDto userDto = userService.getUserById(userId); // Ensure this returns ResponseUserDto
        return new ResponseEntity<>(userDto, HttpStatus.OK); // Return status 200
    }
}