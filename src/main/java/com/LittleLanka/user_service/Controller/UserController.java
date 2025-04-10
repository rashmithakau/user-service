package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.request.RequestLoginDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;
import com.LittleLanka.user_service.Entities.enums.UserStatus;
import com.LittleLanka.user_service.DTOs.response.ResponseUserWithPermissionsDto;
import com.LittleLanka.user_service.Entities.enums.UserStatus;

import com.LittleLanka.user_service.Service.UserService;
import com.LittleLanka.user_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173/")
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/save-user")
    public ResponseEntity<ResponseUserDto> saveUser(@RequestBody RequestSaveUserDTO requestSaveUserDTO) {
        ResponseUserDto responseUserDto = userService.saveUser(requestSaveUserDTO);
        return new ResponseEntity<>(responseUserDto, HttpStatus.CREATED); // Return status 201
    }

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

    @PutMapping("/update-status/{userId}")
    public ResponseEntity<String> updateUserStatus(
            @PathVariable Long userId,
            @RequestBody String status
    ) {
        userService.updateUserStatus(userId, status);
        return ResponseEntity.ok("Status updated successfully");
    }



    @PostMapping("/login")
    public ResponseEntity<StandardResponse> getUserByUserNamPwd(@RequestBody RequestLoginDto requestLoginDto) {
        ResponseUserDto userDto = userService.getUserByUserNamPwd(requestLoginDto);
        return new ResponseEntity<>(
                new StandardResponse(HttpStatus.OK.value(), "Successfully retrieved user",userDto),
                HttpStatus.OK);
    }


//    @GetMapping("/get-users-by-status")
//   public ResponseEntity<List<ResponseUserDto>> getUsersByStatus(@RequestParam("status") UserStatus status) {
//        List<ResponseUserDto> users = userService.getAllUsers()
//                .stream()
//                .filter(user -> user.getStatus().equalsIgnoreCase(status.name()))
//                .toList();
//       return new ResponseEntity<>(users, HttpStatus.OK); // Response with status 200
//    }


    @GetMapping("/get-user-with-permissions/{userId}")
    public ResponseEntity<ResponseUserWithPermissionsDto> getUserWithPermissionsById(@PathVariable Long userId) {
        ResponseUserWithPermissionsDto userWithPermissions = userService.getUserWithPermissionsById(userId);
        return new ResponseEntity<>(userWithPermissions, HttpStatus.OK); // Return status 200
    }


    @GetMapping("/get-users-by-status")
    public ResponseEntity<List<ResponseUserDto>> getUsersByStatus(@RequestParam UserStatus status) {
        List<ResponseUserDto> users = userService.getUsersByStatus(status);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update-phone/{userId}")
    public ResponseEntity<String> updatePhoneNumber(
            @PathVariable Long userId,
            @RequestBody String newPhoneNumber
    ) {
        userService.updatePhoneNumber(userId, newPhoneNumber);
        return new ResponseEntity<>("Phone number updated successfully", HttpStatus.OK);
    }

    @PostMapping("/save-staff")
    public ResponseEntity<ResponseUserDto> saveStaffUser(@RequestBody RequestSaveUserDTO requestSaveUserDTO) {
        ResponseUserDto responseUserDto = userService.saveStaffUser(requestSaveUserDTO);
        return new ResponseEntity<>(responseUserDto, HttpStatus.CREATED);
    }

    // In UserController.java
    @PostMapping("/save-outlet-user")
    public ResponseEntity<ResponseUserDto> saveOutletUser(@RequestBody RequestSaveUserDTO requestSaveUserDTO) {
        ResponseUserDto responseUserDto = userService.saveOutletUser(requestSaveUserDTO);
        return new ResponseEntity<>(responseUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/get-outlet-users")
    public ResponseEntity<List<ResponseUserDto>> getOutletUsers() {
        List<ResponseUserDto> users = userService.getOutletUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}