package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.request.RequestLoginDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;
import com.LittleLanka.user_service.Entities.enums.UserStatus;
import com.LittleLanka.user_service.Service.UserService;
import com.LittleLanka.user_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // Enables Cross-Origin Resource Sharing
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

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
        ResponseUserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK); // Return status 200
    }

    // Endpoint to delete a user
    @DeleteMapping("/deactivate-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deactivateUser(userId);
        return new ResponseEntity<>("User status updated to INACTIVE successfully", HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<StandardResponse> getUserByUserNamPwd(@RequestBody RequestLoginDto requestLoginDto) {
        ResponseUserDto userDto = userService.getUserByUserNamPwd(requestLoginDto);
        return new ResponseEntity<>(
                new StandardResponse(HttpStatus.OK.value(), "Successfully retrieved user",userDto),
                HttpStatus.OK);
    }

    @GetMapping("/get-users-by-status")
    public ResponseEntity<List<ResponseUserDto>> getUsersByStatus(@RequestParam("status") UserStatus status) {
        List<ResponseUserDto> users = userService.getAllUsers()
                .stream()
                .filter(user -> user.getStatus().equalsIgnoreCase(status.name()))
                .toList();

        return new ResponseEntity<>(users, HttpStatus.OK); // Response with status 200
    }

}